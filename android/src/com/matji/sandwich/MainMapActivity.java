package com.matji.sandwich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;

import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.map.MainMatjiMapView;
import com.matji.sandwich.widget.StoreMapNearListView;
import com.matji.sandwich.session.SessionRecentLocationUtil;

public class MainMapActivity extends BaseMapActivity {
    private static final int REQUEST_CODE_LOCATION = 1;
    private static final int BASIC_SEARCH_LOC_LAT = 0;
    private static final int BASIC_SEARCH_LOC_LNG = 0;
    private Context context;
    private MainMatjiMapView mapView;
    private StoreMapNearListView storeListView;
    private TextView addressView;
    private View flipButton;
    private boolean currentViewIsMap;
    // private boolean isFlow;
    private SessionRecentLocationUtil sessionLocationUtil;

    private Drawable flipMapViewBackground;
    private Drawable flipNearStoreBackground;
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_map);
	
	context = getApplicationContext();
	sessionLocationUtil = new SessionRecentLocationUtil(context);
	addressView = (TextView)findViewById(R.id.map_title_bar_address);
	mapView = (MainMatjiMapView)findViewById(R.id.map_view);
	mapView.init(addressView, this);
	storeListView = (StoreMapNearListView)findViewById(R.id.main_map_store_list);
	storeListView.init(addressView, this);
	
	flipButton = findViewById(R.id.map_title_bar_flip_button);
	// isFlow = false;
	currentViewIsMap = true;
	
	flipMapViewBackground = getResources().getDrawable(R.drawable.map_titlebar_flip_mapview_btn);
	flipNearStoreBackground = getResources().getDrawable(R.drawable.map_titlebar_flip_btn);
    }

    protected void onResume() {
	super.onResume();
	mapView.startMapCenterThread();
	// if (!isFlow) {
	//     storeListView.requestReload();
	// } else {
	//     isFlow = false;
	// }
    }

    protected void onNotFlowResume() {
	if (!currentViewIsMap) {
	    storeListView.requestReload();
	}
    }

    protected void onPause() {
	super.onPause();
	mapView.stopMapCenterThread();
    }

    public void onCurrentPositionClicked(View v) {
	if (currentViewIsMap) {
	    mapView.moveToGpsCenter();
	} else {
	    storeListView.moveToGpsCenter();
	}
    }

    public void onFlipClicked(View v) {
	if (currentViewIsMap) {
	    showStoreListView();
	} else {
	    showMapView();
	}
	currentViewIsMap = !currentViewIsMap;
    }

    public void onChangeLocationClicked(View v) {
	startActivityForResult(new Intent(context, ChangeLocationActivity.class), REQUEST_CODE_LOCATION);
    }

    public void onMoveToNearPostClicked(View v) {
	Intent intent = new Intent(context, MainTabActivity.class);
	intent.putExtra(MainTabActivity.IF_INDEX, MainTabActivity.IV_INDEX_POST);
	intent.putExtra(MainTabActivity.IF_SUB_INDEX, 1);
	startActivity(intent);
    }

    // public void setIsFlow(boolean isFlow) {
    // 	this.isFlow = isFlow;
    // }

    private void showMapView() {
	flipButton.setBackgroundDrawable(flipNearStoreBackground);
	mapView.setVisibility(View.VISIBLE);
	storeListView.setVisibility(View.GONE);
	mapView.notifySessionChanged();
    }

    private void showStoreListView() {
	flipButton.setBackgroundDrawable(flipNearStoreBackground);
	storeListView.requestReload();
	mapView.setVisibility(View.GONE);
	storeListView.setVisibility(View.VISIBLE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	switch(requestCode) {
	case REQUEST_CODE_LOCATION:
	    if (resultCode == Activity.RESULT_OK) {
		int searchedLat = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE, BASIC_SEARCH_LOC_LAT);
		int searchedLng = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE, BASIC_SEARCH_LOC_LNG);
		String searchedLocation = data.getStringExtra(ChangeLocationActivity.INTENT_KEY_LOCATION_NAME);
		sessionLocationUtil.push(searchedLocation, searchedLat, searchedLng);
		if (currentViewIsMap) 
		    mapView.setCenter(new GeoPoint(searchedLat, searchedLng));
		else {
		    storeListView.setCenter(new GeoPoint(searchedLat, searchedLng));
		    storeListView.forceReload();
		}
	    }
	}
    }
}
