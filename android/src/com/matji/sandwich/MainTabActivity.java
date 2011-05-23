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
	setContentView(R.layout.main_tab);
	
	tabHost = getTabHost();

	tabHost.addTab(tabHost.newTabSpec("tab3")
		       .setIndicator(getString(R.string.default_string_map))
		       .setContent(new Intent(this, MainMapActivity.class)));
    
	tabHost.addTab(tabHost.newTabSpec("tab2")
		       .setIndicator(getString(R.string.default_string_store))
		       .setContent(new Intent(this, StoreSliderActivity.class)));
    
<<<<<<< HEAD

	tabHost.addTab(tabHost.newTabSpec("tab1")

		       .setIndicator("설정")
		       .setContent(new Intent(this, SettingActivity.class)));
=======
	tabHost.addTab(tabHost.newTabSpec("tab1")
		       .setIndicator(getString(R.string.default_string_alarm))
		       .setContent(new Intent(this, AlarmActivity.class)));

//	tabHost.addTab(tabHost.newTabSpec("tab1")
//		       .setIndicator("마이페이지")
//		       .setContent(new Intent(this, UserTabActivity.class)));

>>>>>>> 5190e625510e6d31e9214f9b0276debe63278e0c
    }
}