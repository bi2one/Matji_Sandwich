package com.matji.sandwich;

import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.map.MatjiMapView;
import com.matji.sandwich.map.MatjiMapCenterListener;
import com.matji.sandwich.session.SessionMapUtil;

public class GetMapPositionRadiusActivity extends BaseMapActivity implements MatjiMapCenterListener {
    public static final String INTENT_KEY_NE_LAT = "GetLocationRadiusActivity.intent_key_ne_lat";
    public static final String INTENT_KEY_SW_LAT = "GetLocationRadiusActivity.intent_key_sw_lat";
    public static final String INTENT_KEY_NE_LNG = "GetLocationRadiusActivity.intent_key_ne_lng";
    public static final String INTENT_KEY_SW_LNG = "GetLocationRadiusActivity.intent_key_sw_lng";
    private static final int LAT_SPAN = (int)(0.005 * 1E6);
    private static final int LNG_SPAN = (int)(0.005 * 1E6);
    private Context context;
    private MatjiMapView mapView;
    private MapController mapController;
    private SessionMapUtil sessionMapUtil;
    private WritePostStoreActivityGroup parentActivity;

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_get_location_radius);

	context = getApplicationContext();
	sessionMapUtil = new SessionMapUtil(context);
	parentActivity = (WritePostStoreActivityGroup)getParent();
	mapView = (MatjiMapView)findViewById(R.id.activity_get_location_radius_map);
	mapController = mapView.getController();

	mapView.setMapCenterListener(this);
	mapController.zoomToSpan(LAT_SPAN, LNG_SPAN);
    }

    protected void onResume() {
	super.onResume();
	mapView.startMapCenterThread();
    }

    protected void onPause() {
	super.onResume();
	mapView.stopMapCenterThread();
    }

    public void setCenter(GeoPoint point) { }

    public void onMapCenterChanged(GeoPoint point) {
	
	Log.d("=====", point.toString());
    }

    public void onTestClick(View view) {
	finish();
    }
}