package com.matji.sandwich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
//import com.google.android.maps.Overlay;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.CoordinateRegion;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.overlay.StoreItemizedOverlay;
import com.matji.sandwich.map.MatjiMapView;
import com.matji.sandwich.map.MatjiMapCenterListener;
import com.matji.sandwich.session.Session;

import java.util.ArrayList;
//import java.util.List;


public class MainMapActivity extends BaseMapActivity implements MatjiLocationListener, MatjiMapCenterListener, Requestable{
    private static final int LAT_SPAN = (int)(0.005 * 1E6);
    private static final int LNG_SPAN = (int)(0.005 * 1E6);
    private static final int MAX_STORE_COUNT = 60;
    private static final int NEARBY_STORE = 1;
    
    private Context mContext;
    private GpsManager mGpsManager;
    private MatjiMapView mMapView;
    private MapController mMapController;
    private Location prevLocation;
    //private List<Overlay> mapOverlays;
    private StoreItemizedOverlay storeItemizedOverlay;
    private ArrayList<MatjiData> stores;
    private HttpRequestManager mRequestManager;
    private Session session;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_map);
	mMapView = (MatjiMapView)findViewById(R.id.map_view);
	mMapController = mMapView.getController();
	mMapView.setMapCenterListener(this);
	mContext = getApplicationContext();
	mGpsManager = new GpsManager(mContext, this);
	mRequestManager = new HttpRequestManager(mContext, this);
	storeItemizedOverlay = new StoreItemizedOverlay(mContext, mMapView);
	session = Session.getInstance(mContext);

	mGpsManager.start();
	mMapView.startMapCenterThread();
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
	storeItemizedOverlay.getOverlayItems().clear();
		
	for (MatjiData storeData : stores){
	    Store store = (Store)storeData;
		    
	    storeItemizedOverlay.addOverlay(store);
	}
	mMapView.postInvalidate();
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch (tag) {
	case NEARBY_STORE:
	    stores = data;
	    drawOverlays();
	    break;		
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
	    mRequestManager.request(mActivity, request, NEARBY_STORE);
	}
    }


    @Override
	protected String titleBarText() {
	// TODO Auto-generated method stub
	return "MainMapActivity";
    }

    @Override
	protected boolean setTitleBarButton(Button button) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
	protected void onTitleBarItemClicked(View view) {
	// TODO Auto-generated method stub
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
	// Intent storeRegisterIntent = new Intent(mContext, GetMapPositionActivity.class);
	Intent storeRegisterIntent = new Intent(mContext, StoreRegisterListActivity.class);
	startActivity(storeRegisterIntent);

	
    }
}
