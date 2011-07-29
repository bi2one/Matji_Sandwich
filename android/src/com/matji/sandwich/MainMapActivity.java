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

public class MainMapActivity extends BaseMapActivity {
    private Context context;
    private MainMatjiMapView mapView;
    private TextView addressView;
    private View flipButton;

    private Drawable flipMapViewBackground;
    private Drawable flipNearStoreBackground;
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_map);
	
	context = getApplicationContext();
	addressView = (TextView)findViewById(R.id.map_title_bar_address);
	mapView = (MainMatjiMapView)findViewById(R.id.map_view);
	mapView.init(addressView, this);
	
	flipButton = findViewById(R.id.map_title_bar_flip_button);
	
	flipMapViewBackground = getResources().getDrawable(R.drawable.map_titlebar_flip_mapview_btn);
	flipNearStoreBackground = getResources().getDrawable(R.drawable.map_titlebar_flip_btn);

	flipButton.setClickable(false);
    }

    protected void onResume() {
	super.onResume();
	mapView.startMapCenterThread();
    }

    protected void onPause() {
	super.onPause();
	mapView.stopMapCenterThread();
    }

    public void onCurrentPositionClick(View v) {
    }
}
