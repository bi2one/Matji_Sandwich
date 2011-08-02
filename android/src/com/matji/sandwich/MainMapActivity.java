package com.matji.sandwich;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.map.MainMatjiMapView;
import com.matji.sandwich.widget.StoreMapNearListView;

public class MainMapActivity extends BaseMapActivity {
    private Context context;
    private MainMatjiMapView mapView;
    private StoreMapNearListView storeListView;
    private TextView addressView;
    private View flipButton;
    private boolean currentViewIsMap;

    private Drawable flipMapViewBackground;
    private Drawable flipNearStoreBackground;
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_map);
	
	context = getApplicationContext();
	addressView = (TextView)findViewById(R.id.map_title_bar_address);
	mapView = (MainMatjiMapView)findViewById(R.id.map_view);
	mapView.init(addressView, this);
	storeListView = (StoreMapNearListView)findViewById(R.id.main_map_store_list);
	storeListView.init(addressView, this);
	
	flipButton = findViewById(R.id.map_title_bar_flip_button);
	
	flipMapViewBackground = getResources().getDrawable(R.drawable.map_titlebar_flip_mapview_btn);
	flipNearStoreBackground = getResources().getDrawable(R.drawable.map_titlebar_flip_btn);
    }

    protected void onResume() {
	super.onResume();
	mapView.startMapCenterThread();
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
}
