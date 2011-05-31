package com.matji.sandwich;

import android.content.Context;
import android.os.Bundle;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.overlay.HelloItemizedOverlay;

import java.util.List;

public class MainMapActivity extends MapActivity implements MatjiLocationListener {
    private static final int LAT_SPAN = (int)(0.005 * 1E6);
    private static final int LNG_SPAN = (int)(0.005 * 1E6);
    
    private Context mContext;
    private GpsManager mGpsManager;
    private MapView mMapView;
    private MapController mMapController;
    private Location prevLocation;
    private List<Overlay> mapOverlays;
    private HelloItemizedOverlay itemizedOverlay;
    
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_map);
	mMapView = (MapView)findViewById(R.id.map_view);
	mMapController = mMapView.getController();

	mContext = getApplicationContext();

	mGpsManager = new GpsManager(mContext, this);
    }

    protected void onResume() {
	super.onResume();
	mGpsManager.start();
    }

    protected void onPause() {
	super.onPause();
	mGpsManager.stop();
    }

    private void setCenter(Location loc) {
	int geoLat = (int)(loc.getLatitude() * 1E6);
	int geoLng = (int)(loc.getLongitude() * 1E6);
	GeoPoint geopoint = new GeoPoint(geoLat, geoLng);
	mMapController.animateTo(geopoint);
	
	mapOverlays = mMapView.getOverlays();
        itemizedOverlay = new HelloItemizedOverlay(getResources().getDrawable(R.drawable.icon));
	OverlayItem overlayitem = new OverlayItem(geopoint, "", "");
	itemizedOverlay.addOverlay(overlayitem);
	mapOverlays.add(itemizedOverlay);
	
	mMapController.setCenter(geopoint);
	mMapController.zoomToSpan(LAT_SPAN, LNG_SPAN);
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

    protected boolean isRouteDisplayed() {
    	return true;
    }

    
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
