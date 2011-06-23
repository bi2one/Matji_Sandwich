package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.http.util.DisplayUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainTabActivity extends BaseTabActivity {
    public static final String IF_INDEX = "index";
    public static final int IV_INDEX_STORE = 1;
    public static final int IV_INDEX_POST = 2;
	
    private TabHost tabHost;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	DisplayUtil.setContext(getApplicationContext());
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
		       .setIndicator(getString(R.string.default_string_configure))
		       .setContent(new Intent(this, SettingActivity.class)));
	
    }

    protected void onNewIntent(Intent intent) {
	super.onNewIntent(intent);
	tabHost.setCurrentTab(intent.getIntExtra(IF_INDEX, 0));
    }
}