package com.matji.sandwich;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class StoreTabActivity extends TabActivity{
	TabHost tabHost;
	Intent intent;
	
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	intent = getIntent();

	Intent mainIntent = new Intent(this, StoreMainActivity.class);
	mainIntent.putExtra("store", intent.getParcelableExtra("store"));

	tabHost = getTabHost();
	
	tabHost.addTab(tabHost.newTabSpec("main")
    		.setIndicator("메인")
    		.setContent(mainIntent));
    tabHost.addTab(tabHost.newTabSpec("memo")
			.setIndicator("메모")
			.setContent(new Intent(this, StoreMoreActivity.class)));
    tabHost.addTab(tabHost.newTabSpec("menu")
			.setIndicator("메뉴")
			.setContent(new Intent(this, StoreMoreActivity.class)));
    tabHost.addTab(tabHost.newTabSpec("image")
			.setIndicator("이미지")
			.setContent(new Intent(this, StoreMoreActivity.class)));
    tabHost.addTab(tabHost.newTabSpec("more")
			.setIndicator("More")
			.setContent(new Intent(this, StoreMoreActivity.class)));
	}	
}

