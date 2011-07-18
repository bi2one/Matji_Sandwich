package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StoreUrlListView;

import android.os.Bundle;

public class StoreUrlListActivity extends BaseActivity {
	private int store_id;		
	private StoreUrlListView listView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_url);

		store_id = getIntent().getIntExtra("store_id", 0);
		
		listView = (StoreUrlListView) findViewById(R.id.store_url_list);
		listView.setUserId(store_id);
		listView.setActivity(this);
		listView.requestReload();
	}
}