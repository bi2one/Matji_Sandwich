package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.matji.sandwich.base.BaseTabActivity;

public class StoreDetailInfoTabActivity extends BaseTabActivity {
	private Intent defaultIntent;
	private Intent noteIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		defaultIntent = new Intent(this, StoreDefaultInfoActivity.class);
		noteIntent = new Intent(this, StoreNoteListActivity.class);
		
		TabHost tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator(getString(R.string.store_detail_info_default))
				.setContent(defaultIntent));
		tabHost.addTab(tabHost.newTabSpec("tab2")
				.setIndicator(getString(R.string.store_detail_info_note))
				.setContent(noteIntent));
	}
}