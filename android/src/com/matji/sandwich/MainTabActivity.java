package com.matji.sandwich;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.TabHost.OnTabChangeListener;

public class MainTabActivity extends TabActivity{
	TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	
	tabHost = getTabHost();

    tabHost.addTab(tabHost.newTabSpec("tab3")
			.setIndicator("지도")
			.setContent(new Intent(this, MatjiMapActivity.class)));
    
	tabHost.addTab(tabHost.newTabSpec("tab2")
    		.setIndicator("맛집")
    		.setContent(new Intent(this, FlipperTestActivity.class)));
    
    tabHost.addTab(tabHost.newTabSpec("tab1")
			.setIndicator("알람")
			.setContent(new Intent(this, AlarmActivity.class)));
	}
}