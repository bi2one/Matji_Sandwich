package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StoreMenuListView;

import android.content.Intent;
import android.os.Bundle;

public class StoreMenuActivity extends BaseActivity {
	private Intent intent;
	private int store_id;
	private StoreMenuListView listView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_menu);

		intent = getIntent();
		store_id = intent.getIntExtra("store_id", 0);
		
		listView = (StoreMenuListView) findViewById(R.id.store_menu_list);
		listView.setUserId(store_id);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected String usedTitleBar() {
		return null;
	}
}