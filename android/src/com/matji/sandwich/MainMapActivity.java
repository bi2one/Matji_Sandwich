package com.matji.sandwich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
// import android.view.inputmethod.InputMethodManager;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.CoordinateRegion;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.AddressMatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.overlay.StoreItemizedOverlay;
import com.matji.sandwich.map.MatjiMapView;
import com.matji.sandwich.map.MatjiMapCenterListener;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.GeocodeUtil;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.List;

public class MainMapActivity extends BaseMapActivity implements MatjiLocationListener,
								MatjiMapCenterListener,
								Requestable,
								OnKeyListener,
								OnTouchListener {
    public static final String RETURN_KEY_LAT_NE = "MainMapActivity.return_key_lat_ne";
    public static final String RETURN_KEY_LAT_SW = "MainMapActivity.return_key_lat_sw";
    public static final String RETURN_KEY_LNG_NE = "MainMapActivity.return_key_lng_ne";
    public static final String RETURN_KEY_LNG_SW = "MainMapActivity.return_key_lng_sw";
    private static final int BOOKMARK_BASIC_LAT_NE = 100;
    private static final int BOOKMARK_BASIC_LAT_SW = 100;
    private static final int BOOKMARK_BASIC_LNG_NE = 100;
    private static final int BOOKMARK_BASIC_LNG_SW = 100;
    private static final int LAT_SPAN = (int)(0.005 * 1E6);
    private static final int LNG_SPAN = (int)(0.005 * 1E6);
    private static final int MAX_STORE_COUNT = 60;
    private static final int NEARBY_STORE = 1;
    private static final int SEARCH_LOCATION = 2;
    private static final int GEOCODE = 3;
    private static final int GET_BOOKMARK_POSITION_TAG = 0;

    private Context mContext;
    private GpsManager mGpsManager;
    private MatjiMapView mMapView;
    private MapController mMapController;
    private Location prevLocation;
    private GeoPoint currentCenterPoint;
    private GeoPoint currentNeBound;
    private GeoPoint currentSwBound;
    private StoreItemizedOverlay storeItemizedOverlay;
    private ArrayList<MatjiData> stores;
    private HttpRequestManager mRequestManager;
    private Session session;
    private TextView addressView;
    // private InputMethodManager imm;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_map);
	mMapView = (MatjiMapView)findViewById(R.id.map_view);
	mMapController = mMapView.getController();
	mMapView.setMapCenterListener(this);
	mMapView.setOnTouchListener(this);
	mContext = getApplicationContext();
	mGpsManager = new GpsManager(mContext, this);
	addressView = (TextView)findViewById(R.id.map_title_bar_address);
	mRequestManager = HttpRequestManager.getInstance(mContext);
	storeItemizedOverlay = new StoreItemizedOverlay(mContext, this, mMapView);
	session = Session.getInstance(mContext);

	mGpsManager.start();
    }

    protected void onResume() {
	super.onResume();
	mMapView.startMapCenterThread();
    }

    protected void onPause() {
	super.onPause();
	mGpsManager.stop();
	mMapView.stopMapCenterThread();
	mRequestManager.turnOn();
    }

    private void setCenter(Location loc) {
	int geoLat = (int)(loc.getLatitude() * 1E6);
	int geoLng = (int)(loc.getLongitude() * 1E6);
	GeoPoint geoPoint = new GeoPoint(geoLat, geoLng);
	mMapController.animateTo(geoPoint);
	mMapController.zoomToSpan(LAT_SPAN, LNG_SPAN);
    }

    public void onLocationChanged(Location location) {
	currentCenterPoint = new GeoPoint((int)(location.getLatitude()*1E6),
					  (int)(location.getLongitude()*1E6));
	currentNeBound = mMapView.getBound(MatjiMapView.BoundType.MAP_BOUND_NE);
	currentSwBound = mMapView.getBound(MatjiMapView.BoundType.MAP_BOUND_SW);

	if (prevLocation != null) {
	    if (prevLocation.getAccuracy() >= location.getAccuracy()) {
		mGpsManager.stop();
	    }
	}

	prevLocation = location;
	setCenter(prevLocation);
    }

    public void onLocationExceptionDelivered(MatjiException e) {
	e.performExceptionHandling(mContext);
    }

    public void onMapCenterChanged(GeoPoint point) {
	currentCenterPoint = point;
	currentNeBound = mMapView.getBound(MatjiMapView.BoundType.MAP_BOUND_NE);
	currentSwBound = mMapView.getBound(MatjiMapView.BoundType.MAP_BOUND_SW);

	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LATITUDE_NE, currentNeBound.getLatitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LATITUDE_SW, currentSwBound.getLatitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LONGITUDE_NE, currentNeBound.getLongitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LONGITUDE_SW, currentSwBound.getLongitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_CENTER_LATITUDE, point.getLatitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_CENTER_LONGITUDE, point.getLongitudeE6());

	Runnable runnable = new MapRunnable(this);
	runOnUiThread(runnable);
    }

    private void drawOverlays() {
	mMapView.getOverlays().clear();
	storeItemizedOverlay.getOverlayItems().clear();

	for (MatjiData storeData : stores){
	    Store store = (Store)storeData;

	    storeItemizedOverlay.addOverlay(store);
	}
	storeItemizedOverlay.mPopulate();
	mMapView.postInvalidate();
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch (tag) {
	case NEARBY_STORE:
	    stores = data;
	    Collections.reverse(stores);
	    drawOverlays();
	    break;
	case SEARCH_LOCATION:
	    Address address = ((AddressMatjiData)data.get(0)).getAddress();
	    GeoPoint point = new GeoPoint((int)(address.getLatitude() * 1E6), (int)(address.getLongitude() * 1E6));
	    mMapController.animateTo(point);
	    break;
	case GEOCODE:
	    GeocodeAddress geocodeAddress = GeocodeUtil.approximateAddress(data, currentNeBound, currentSwBound);
	    addressView.setText(geocodeAddress.getShortenFormattedAddress());
	    // if (((AddressMatjiData)data.get(0)).getAddress().getPremises() != null) {
		// Log.d("=====", ((AddressMatjiData)data.get(0)).getAddress().getPremises());
	    // } else {
		// Log.d("=====", "null: " + ((AddressMatjiData)data.get(0)).getAddress().getAddressLine(0));
	    // }
	    // for ((AddressMatjiData) addressData : data) {
	    // 	Address address = addressData.getAddress();
		
	    // }
	}
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(mContext);
    }

    protected boolean isRouteDisplayed() {
	return true;
    }

    public class MapRunnable implements Runnable{
	private Activity mActivity;

	public MapRunnable(Activity activity) {
	    this.mActivity = activity;
	}

	public void run() {
	    loadStoresOnMap();
	}

	private void loadStoresOnMap(){
	    StoreHttpRequest request = new StoreHttpRequest(mContext);
	    GeocodeHttpRequest geocodeRequest = new GeocodeHttpRequest(mContext);
	    
	    CoordinateRegion cr = new CoordinateRegion(mMapView.getMapCenter(), mMapView.getLatitudeSpan(), mMapView.getLongitudeSpan());
	    GeoPoint swPoint = cr.getSWGeoPoint();
	    GeoPoint nePoint = cr.getNEGeoPoint();

	    double lat_sw = (double)(swPoint.getLatitudeE6()) / (double)1E6;
	    double lng_sw = (double)(swPoint.getLongitudeE6()) / (double)1E6;
	    double lat_ne = (double)(nePoint.getLatitudeE6()) / (double)1E6;
	    double lng_ne = (double)(nePoint.getLongitudeE6()) / (double)1E6;

	    request.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, 1, MAX_STORE_COUNT);
	    geocodeRequest.actionReverseGeocodingByGeoPoint(mMapView.getMapCenter(), GeocodeHttpRequest.Country.KOREA);
	    // geocodeRequest.actionFromGeoPoint(mMapView.getMapCenter(), 10);
	    mRequestManager.request(mActivity, request, NEARBY_STORE, (Requestable)mActivity);
	    mRequestManager.request(mActivity, geocodeRequest, GEOCODE, (Requestable)mActivity);
	}
    }

    public void onNearStoreClick(View v) {
	session.getPreferenceProvider().setInt(Session.STORE_SLIDER_INDEX, StoreSliderActivity.INDEX_NEAR_STORE);

	Intent tabIntent = new Intent(mContext, MainTabActivity.class);
	tabIntent.putExtra(MainTabActivity.IF_INDEX, MainTabActivity.IV_INDEX_STORE);
	startActivity(tabIntent);
    }

    public void onNearPostClick(View v) {
	session.getPreferenceProvider().setInt(Session.POST_SLIDER_INDEX, PostSliderActivity.INDEX_NEAR_POST);

	Intent tabIntent = new Intent(mContext, MainTabActivity.class);
	tabIntent.putExtra(MainTabActivity.IF_INDEX, MainTabActivity.IV_INDEX_POST);
	startActivity(tabIntent);
    }

    public void onCurrentPositionClick(View v) {
	mRequestManager.cancelTask();
	mGpsManager.start();
    }

    public void onStoreRegisterClick(View v) {
	if (loginRequired()) {
	    Intent storeRegisterIntent = new Intent(mContext, StoreRegisterListActivity.class);
	    startActivity(storeRegisterIntent);
	}
    }

    public void onNearRankingClick(View v) {
	GeoPoint neBound = mMapView.getBound(MatjiMapView.BoundType.MAP_BOUND_NE);
	GeoPoint swBound = mMapView.getBound(MatjiMapView.BoundType.MAP_BOUND_SW);
	int neLat = neBound.getLatitudeE6();
	int neLng = neBound.getLongitudeE6();
	int swLat = swBound.getLatitudeE6();
	int swLng = swBound.getLongitudeE6();

	Intent userNearRankingIntent = new Intent(mContext, UserNearRankingActivity.class);
	userNearRankingIntent.putExtra(UserNearRankingActivity.IF_LAT_NE, neLat);
	userNearRankingIntent.putExtra(UserNearRankingActivity.IF_LNG_NE, neLng);
	userNearRankingIntent.putExtra(UserNearRankingActivity.IF_LAT_SW, swLat);
	userNearRankingIntent.putExtra(UserNearRankingActivity.IF_LNG_SW, swLng);

	startActivity(userNearRankingIntent);
    }

    // public void onBookmarkClick(View v) {
    // 	if (loginRequired()) {
    // 	    bookmarkButton.setClickable(false);
    // 	    Intent bookmarkIntent = new Intent(mContext, BookmarkListActivity.class);

    // 	    if (currentNeBound != null && currentSwBound != null) {
    // 		double latNe = (double)currentNeBound.getLatitudeE6() / 1E6;
    // 		double lngNe = (double)currentNeBound.getLongitudeE6() / 1E6;
    // 		double latSw = (double)currentSwBound.getLatitudeE6() / 1E6;
    // 		double lngSw = (double)currentSwBound.getLongitudeE6() / 1E6;

    // 		bookmarkIntent.putExtra(BookmarkListActivity.IF_LAT_NE, latNe);
    // 		bookmarkIntent.putExtra(BookmarkListActivity.IF_LNG_NE, lngNe);
    // 		bookmarkIntent.putExtra(BookmarkListActivity.IF_LAT_SW, latSw);
    // 		bookmarkIntent.putExtra(BookmarkListActivity.IF_LNG_SW, lngSw);
    // 	    }
    // 	    startActivityForResult(bookmarkIntent, GET_BOOKMARK_POSITION_TAG);
    // 	}
    // }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);

	if (resultCode == Activity.RESULT_OK) {
	    switch(requestCode) {
	    case GET_BOOKMARK_POSITION_TAG:
		int latNe = data.getIntExtra(RETURN_KEY_LAT_NE, BOOKMARK_BASIC_LAT_NE);
		int latSw = data.getIntExtra(RETURN_KEY_LAT_SW, BOOKMARK_BASIC_LAT_SW);
		int lngNe = data.getIntExtra(RETURN_KEY_LNG_NE, BOOKMARK_BASIC_LNG_NE);
		int lngSw = data.getIntExtra(RETURN_KEY_LNG_SW, BOOKMARK_BASIC_LNG_SW);
		int latSpan = Math.abs(latNe - latSw);
		int lngSpan = Math.abs(lngNe - lngSw);
		int centerLat = (latNe + latSw) / 2;
		int centerLng = (lngNe + lngSw) / 2;

		mMapController.animateTo(new GeoPoint(centerLat, centerLng));
		mMapController.zoomToSpan(latSpan, lngSpan);
	    }
	}
	// bookmarkButton.setClickable(true);
    }

    private void findAndMovePosition(String seed) {
	// GeocodeHttpRequest request = new GeocodeHttpRequest(mContext);
	// request.actionFromLocationName(seed, 1);
	// mRequestManager.request(this, request, SEARCH_LOCATION, this);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
	// if (v.getId() == mSearchView.getId()) {
	//     if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
	// 	findAndMovePosition(mSearchView.getText().toString());
	//     }
	// }
	return false;
    }

    public boolean onTouch(View v, MotionEvent e) {
	switch(e.getAction()) {
	case MotionEvent.ACTION_DOWN:
	    mGpsManager.stop();
	    mRequestManager.turnOff();
	    break;
	case MotionEvent.ACTION_UP:
	    mRequestManager.turnOn();
	}
	return false;
    }
}
