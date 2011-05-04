package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.location.Location;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import com.matji.sandwich.util.GpsManager;

public class MatjiMapActivity extends MapActivity{
    //private constant
    private GpsManager gpsmanager;
    private MapView map;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
    }

    private void setMarker(Location loc){

    }

    private void setSmallmarker(Location loc){

    }

    protected boolean isRouteDisplayed() {
	return true;
    }
}
