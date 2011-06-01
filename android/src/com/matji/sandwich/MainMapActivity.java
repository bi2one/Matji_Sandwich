package com.matji.sandwich;

import android.content.Context;
import android.os.Bundle;
import android.location.Location;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.overlay.StoreItemizedOverlay;
import com.matji.sandwich.map.MatjiMapView;
import com.matji.sandwich.map.MatjiMapCenterListener;

import java.util.List;

public class MainMapActivity extends MapActivity implements MatjiLocationListener, MatjiMapCenterListener {
    private static final int LAT_SPAN = (int)(0.005 * 1E6);
    private static final int LNG_SPAN = (int)(0.005 * 1E6);
    
    private Context mContext;
    private GpsManager mGpsManager;
    private MatjiMapView mMapView;
    private MapController mMapController;
    private Location prevLocation;
    private List<Overlay> mapOverlays;
    private StoreItemizedOverlay storeItemizedOverlay;
    
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_map);
	mMapView = (MatjiMapView)findViewById(R.id.map_view);
	mMapController = mMapView.getController();
	mMapView.setMapCenterListener(this);

	mContext = getApplicationContext();

	mGpsManager = new GpsManager(mContext, this);

        storeItemizedOverlay = new StoreItemizedOverlay(mContext, mMapView);
    }

    protected void onResume() {
	super.onResume();
	mGpsManager.start();
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
	// storeItemizedOverlay.addOverlay(geoPoint);
    }

    public void onLocationChanged(Location location) {
	if (prevLocation != null) {
	    if (prevLocation.getAccuracy() <= location.getAccuracy()) {
		mGpsManager.stop();
		return;
	    }
	}
    
	prevLocation = location;
	setCenter(prevLocation);
    }

    public void onLocationExceptionDelivered(MatjiException e) {
	e.performExceptionHandling(mContext);
    }

    public void onMapCenterChanged(GeoPoint point) {
	Runnable runnable = new MapRunnable(point);
	runOnUiThread(runnable);
    }

    protected boolean isRouteDisplayed() {
    	return true;
    }

    public class MapRunnable implements Runnable {
	private GeoPoint point;
	
	public MapRunnable(GeoPoint point) {
	    this.point = point;
	}

	public void run() {
	    storeItemizedOverlay.addOverlay(point);
	    mMapView.postInvalidate();
	}
    }

    // public boolean dispatchTouchEvent(MotionEvent event) {
    // 	boolean result = super.dispatchTouchEvent(event);
    // 	if (event.getAction() == MotionEvent.ACTION_UP) {
    // 	    // Log.d("======", mMapView.getMapCenter().toString());
    // 	    storeItemizedOverlay.addOverlay(mMapView.getMapCenter());
    // 	}
    // 	return result;
    // }

    // 	e = (EditText) findViewById(R.id.main_map_search_bar);
    // 	Button b = (Button) findViewById(R.id.main_map_gps_button);
    // 	b.requestFocus();
    // 	//		e.setOnFocusChangeListener(new MyFocusChangeListener());
    // }

    // private void setMarker(Location loc){

    // }

    // private void setSmallmarker(Location loc){

    // }

    // class MyFocusChangeListener implements OnFocusChangeListener {
    // 	public void onFocusChange(View v, boolean hasFocus) {
    // 	    // TODO Auto-generated method stub
    // 	    if(hasFocus) {
    // 		Log.d("Matji", hasFocus + "");
    // 		((EditText) v).setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    // 	    }
    // 	    else {
    // 		Log.d("Matji", hasFocus + "");
    // 		((EditText) v).setLayoutParams(new RelativeLayout.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT));
    // 	    }

    // 	}
		
    // }
}
