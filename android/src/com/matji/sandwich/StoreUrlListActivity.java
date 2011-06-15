package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StoreUrlListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StoreUrlListActivity extends BaseActivity {
	private Intent intent;
	private int store_id;		
	private StoreUrlListView listView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_url);

		intent = getIntent();
		store_id = intent.getIntExtra("store_id", 0);
		
		listView = (StoreUrlListView) findViewById(R.id.store_url_list);
		listView.setUserId(store_id);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected String titleBarText() {
		return "StoreUrlListActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		
	}
}