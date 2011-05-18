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

	LayoutInflater.from(this).inflate(R.layout.c14_tabtest, tabHost.getTabContentView(), true);
	    
	tabHost.addTab(tabHost.newTabSpec("tab2")
    		.setIndicator("ssfa")
    		.setContent(R.id.opt_linker));

    tabHost.addTab(tabHost.newTabSpec("tab2")
    		.setIndicator("ssfa")
    		.setContent(R.id.opt_compiler));
    
    tabHost.addTab(tabHost.newTabSpec("tab1")
			.setIndicator("asdfasfd")
			.setContent(R.id.opt_general));
	
	}
}