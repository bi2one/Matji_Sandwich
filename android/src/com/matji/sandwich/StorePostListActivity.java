package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StorePostListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StorePostListActivity extends BaseActivity {
	private Intent intent;
	private int store_id;
	private StorePostListView listView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_post);

		intent = getIntent();
		store_id = intent.getIntExtra("store_id", 0);
		
		listView = (StorePostListView) findViewById(R.id.store_post_list);
		listView.setStoreId(store_id);
		listView.setActivity(this);
		listView.requestReload();		
	}

	@Override
	protected String titleBarText() {
		return "StorePostListActivity";
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