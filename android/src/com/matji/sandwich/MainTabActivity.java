package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.session.*;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.TabHost;

public class MainTabActivity extends BaseTabActivity {
    private TabHost tabHost;
	
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_tab);
	
	tabHost = getTabHost();

	tabHost.addTab(tabHost.newTabSpec("tab1")
		       .setIndicator(getString(R.string.default_string_map))
		       .setContent(new Intent(this, MainMapActivity.class)));
    
	tabHost.addTab(tabHost.newTabSpec("tab2")
		       .setIndicator(getString(R.string.default_string_store))
		       .setContent(new Intent(this, StoreSliderActivity.class)));

	tabHost.addTab(tabHost.newTabSpec("tab3")
		       .setIndicator(getString(R.string.default_string_memo))
		       .setContent(new Intent(this, PostSliderActivity.class)));

	tabHost.addTab(tabHost.newTabSpec("tab4")
		       .setIndicator("설정")
		       .setContent(new Intent(this, SettingActivity.class)));
    }
}