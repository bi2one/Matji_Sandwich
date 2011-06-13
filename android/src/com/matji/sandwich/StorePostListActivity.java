package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StorePostListView;

import android.content.Intent;
import android.os.Bundle;

public class StorePostListActivity extends BaseActivity {
	// TODO 추후 PostActivity들 공통된것 위로 뺄 수 있나?
	private Intent intent;
	private int store_id;
	private StorePostListView listView;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_post);
		
		intent = getIntent();
		store_id = intent.getIntExtra("store_id", 0);
		
		listView = (StorePostListView) findViewById(R.id.store_post_list_view);
		listView.setStoreId(store_id);
		listView.setActivity(this);
		listView.requestReload();
	}
}