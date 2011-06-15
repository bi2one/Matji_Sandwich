package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainTabActivity extends BaseTabActivity {
	private TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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

//	@Override
//	protected String usedTitleBar() {
//		return "MainTabActivity";
//	}
//
//	@Override
//	protected boolean setTitleBarButton(Button button) {
//		button.setText("Info");
//		return true;
//	}
//
//	@Override
//	protected void onTitleBarItemClicked(View view) {
//		// TODO Auto-generated method stub
//	}
}