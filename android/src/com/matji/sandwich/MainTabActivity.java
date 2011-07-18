package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.widget.MainTabHost;
import com.matji.sandwich.widget.indicator.RoundHeadIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.view.View;

public class MainTabActivity extends BaseActivity {
    private MainTabHost tabHost;
    
    public static final String IF_INDEX = "index";
    public static final int IV_INDEX_STORE = 1;
    public static final int IV_INDEX_POST = 2;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_tab);

	tabHost = (MainTabHost)findViewById(R.id.tab_host);

	DisplayUtil.setContext(getApplicationContext());
	// tabHost.addTab(tabHost.newTabSpec("tab1")
	// 	       .setIndicator(getString(R.string.default_string_map))
	// 	       .setContent(new Intent(this, MainMapActivity.class)));
	tabHost.addTab("tab1",
		       R.drawable.icon,
		       R.string.default_string_map,
		       new Intent(this, MainMapActivity.class));

	// tabHost.addTab(tabHost.newTabSpec("tab2")
	// 	       .setIndicator(getString(R.string.default_string_store))
	// 	       .setContent(new Intent(this, StoreSliderActivity.class)));

	// tabHost.addTab(tabHost.newTabSpec("tab3")
	// 	       .setIndicator(getString(R.string.default_string_memo))
	// 	       .setContent(new Intent(this, PostSliderActivity.class)));

	// tabHost.addTab(tabHost.newTabSpec("tab4")
	// 	       .setIndicator(getString(R.string.default_string_configure))
	// 	       .setContent(new Intent(this, SettingActivity.class)));
    }

    protected void onNewIntent(Intent intent) {
	super.onNewIntent(intent);
	tabHost.setCurrentTab(intent.getIntExtra(IF_INDEX, 0));
    }
}