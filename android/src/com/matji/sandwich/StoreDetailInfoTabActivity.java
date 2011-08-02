package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.RoundTabHost;

public class StoreDetailInfoTabActivity extends BaseTabActivity {
	private RoundTabHost tabHost;
	private Store store;
	
	public static final String STORE = "store";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void init() {
		super.init();
		setContentView(R.layout.activity_store_detail_tab);
		store = (Store) getIntent().getParcelableExtra(STORE);
		setTabHost();
	}
	
	private void setTabHost() {
		Intent defaultInfoIntent = new Intent(this, StoreDefaultInfoActivity.class);
		defaultInfoIntent.putExtra(StoreDefaultInfoActivity.STORE, (Parcelable) store);
		
		tabHost = (RoundTabHost)getTabHost();
		
		tabHost.addLeftTab("tab1", 
				R.string.default_string_info, defaultInfoIntent);
		tabHost.addCenterTab("tab2", 
				R.string.default_string_tag, 
				new Intent(this, StoreSliderActivity.class));
		tabHost.addRightTab("tab3", 
				R.string.default_string_menu, 
				new Intent(this, StoreSliderActivity.class));
	}
}