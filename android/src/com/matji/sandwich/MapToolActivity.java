package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseMapActivity;

public class MapToolActivity extends BaseMapActivity {
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
    }

        protected boolean isRouteDisplayed() {
	return true;
    }

}
