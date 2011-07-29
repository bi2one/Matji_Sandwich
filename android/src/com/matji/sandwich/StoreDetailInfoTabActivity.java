package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.RoundTabHost;

public class StoreDetailInfoTabActivity extends BaseTabActivity {
	private RoundTabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_detail_tab);
		
		tabHost = (RoundTabHost)getTabHost();
		
		tabHost.addLeftTab("tab1", 
				R.string.default_string_info, 
				new Intent(this, StoreSliderActivity.class));
		tabHost.addCenterTab("tab2", 
				R.string.default_string_tag, 
				new Intent(this, StoreSliderActivity.class));
		tabHost.addRightTab("tab3", 
				R.string.default_string_menu, 
				new Intent(this, StoreSliderActivity.class));
	}	
}