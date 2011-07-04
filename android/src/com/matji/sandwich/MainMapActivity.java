package com.matji.sandwich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
// import android.view.inputmethod.InputMethodManager;
import android.view.View.OnKeyListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.CoordinateRegion;
import com.matji.sandwich.data.MatjiData;
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

import java.util.ArrayList;
//import java.util.List;

public class MainMapActivity extends BaseMapActivity implements MatjiLocationListener,
								MatjiMapCenterListener,
								Requestable,
								OnKeyListener {
    private static final int LAT_SPAN = (int)(0.005 * 1E6);
    private static final int LNG_SPAN = (int)(0.005 * 1E6);
    private static final int MAX_STORE_COUNT = 60;
    private static final int NEARBY_STORE = 1;
    private static final int SEARCH_LOCATION = 2;

    private Context mContext;
    private GpsManager mGpsManager;
    private MatjiMapView mMapView;
    private MapController mMapController;
    private EditText mSearchView;
    private Location prevLocation;
    private StoreItemizedOverlay storeItemizedOverlay;
    private ArrayList<MatjiData> stores;
    private HttpRequestManager mRequestManager;
    private Session session;
    // private InputMethodManager imm;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_map);
	mMapView = (MatjiMapView)findViewById(R.id.map_view);
	mMapController = mMapView.getController();
	mMapView.setMapCenterListener(this);
	mContext = getApplicationContext();
	mGpsManager = new GpsManager(mContext, this);
	mSearchView = (EditText)findViewById(R.id.main_map_search_box);
	mRequestManager = HttpRequestManager.getInstance(mContext);
	storeItemizedOverlay = new StoreItemizedOverlay(mContext, this, mMapView);
	session = Session.getInstance(mContext);

	mSearchView.setOnKeyListener(this);
	// imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

	mGpsManager.start();
	// mMapView.startMapCenterThread();
    }

    protected void onResume() {
	super.onResume();
	mMapView.startMapCenterThread();
    }

    protected void onPause() {
	super.onPause();
	mGpsManager.stop();
	mMapView.stopMapCenterThread();
    }

    private void setCenter(Location loc) {
	int geoLat = (int)(loc.getLatitude() * 1E6);
	int geoLng = (int)(loc.getLongitude() * 1E6);
	GeoPoint geoPoint = new GeoPoint(geoLat, geoLng);
	mMapController.animateTo(geoPoint);
	mMapController.zoomToSpan(LAT_SPAN, LNG_SPAN);
    }

    public void onLocationChanged(Location location) {
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
	GeoPoint neBound = mMapView.getBound(MatjiMapView.BoundType.MAP_BOUND_NE);
	GeoPoint swBound = mMapView.getBound(MatjiMapView.BoundType.MAP_BOUND_SW);

	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LATITUDE_NE, neBound.getLatitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LATITUDE_SW, swBound.getLatitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LONGITUDE_NE, neBound.getLongitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LONGITUDE_SW, swBound.getLongitudeE6());

	Runnable runnable = new MapRunnable(this);
	runOnUiThread(runnable);
    }

    private void drawOverlays(){
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
	    drawOverlays();
	    break;
	case SEARCH_LOCATION:
	    Address address = ((AddressMatjiData)data.get(0)).getAddress();
	    GeoPoint point = new GeoPoint((int)(address.getLatitude() * 1E6), (int)(address.getLongitude() * 1E6));
	    mMapController.animateTo(point);
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
	    CoordinateRegion cr = new CoordinateRegion(mMapView.getMapCenter(), mMapView.getLatitudeSpan(), mMapView.getLongitudeSpan());
	    GeoPoint swPoint = cr.getSWGeoPoint();
	    GeoPoint nePoint = cr.getNEGeoPoint();

	    double lat_sw = (double)(swPoint.getLatitudeE6()) / (double)1E6;
	    double lng_sw = (double)(swPoint.getLongitudeE6()) / (double)1E6;
	    double lat_ne = (double)(nePoint.getLatitudeE6()) / (double)1E6;
	    double lng_ne = (double)(nePoint.getLongitudeE6()) / (double)1E6;

	    request.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, 1, MAX_STORE_COUNT);
	    mRequestManager.request(mActivity, request, NEARBY_STORE, (Requestable)mActivity);
	}
    }


    @Override
	protected String titleBarText() {
	return "MainMapActivity";
    }

    @Override
	protected boolean setTitleBarButton(Button button) {
	return false;
    }

    @Override
	protected void onTitleBarItemClicked(View view) {
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
	mGpsManager.start();
    }

    public void onStoreRegisterClick(View v) {
	if (loginRequired()) {			
	    Intent storeRegisterIntent = new Intent(mContext, StoreRegisterListActivity.class);
	    startActivity(storeRegisterIntent);
	}
    }

    private void findAndMovePosition(String seed) {
	GeocodeHttpRequest request = new GeocodeHttpRequest(mContext);
	request.actionFromLocationName(seed, 1);
	mRequestManager.request(this, request, SEARCH_LOCATION, this);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
	if (v.getId() == mSearchView.getId()) {
	    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
		findAndMovePosition(mSearchView.getText().toString());
	    }
	}
	return false;
    }
}
