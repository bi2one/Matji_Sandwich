package com.matji.sandwich;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class StoreTabActivity extends TabActivity{
	TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	
	tabHost = getTabHost();
	tabHost.addTab(tabHost.newTabSpec("tab1")
    		.setIndicator("메인")
    		.setContent(new Intent(this, StoreMainActivity.class)));

    tabHost.addTab(tabHost.newTabSpec("tab2")
			.setIndicator("메모")
			.setContent(new Intent(this, StoreMoreActivity.class)));
    tabHost.addTab(tabHost.newTabSpec("tab2")
			.setIndicator("메뉴")
			.setContent(new Intent(this, StoreMoreActivity.class)));
    tabHost.addTab(tabHost.newTabSpec("tab2")
			.setIndicator("이미지")
			.setContent(new Intent(this, StoreMoreActivity.class)));
    tabHost.addTab(tabHost.newTabSpec("tab2")
			.setIndicator("More")
			.setContent(new Intent(this, StoreMoreActivity.class)));
	}	
}

