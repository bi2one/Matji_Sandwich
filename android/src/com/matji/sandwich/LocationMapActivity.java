package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.maps.MapActivity;

public class LocationMapActivity extends MapActivity{
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
    }

    protected boolean isRouteDisplayed() {
	return true;
    }
}