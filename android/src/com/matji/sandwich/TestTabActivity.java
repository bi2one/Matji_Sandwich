package com.matji.sandwich;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TestTabActivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		TabHost tabHost = this.getTabHost();
		tabHost.addTab(tabHost.newTabSpec("tab 1").setIndicator("탭 1").setContent(new Intent(this, TestTabItem1.class)));
		tabHost.addTab(tabHost.newTabSpec("tab 2").setIndicator("탭 2").setContent(new Intent(this, TestTabItem2.class)));
		tabHost.addTab(tabHost.newTabSpec("tab 3").setIndicator("탭 3").setContent(new Intent(this, TestTabItem3.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
	}
}
