package com.matji.sandwich;

import com.matji.sandwich.widget.StorePostListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StorePostActivity extends Activity {
	// TODO 추후 PostActivity들 공통된것 위로 뺄 수 있나?
	private Intent intent;
	private int store_id;
	private StorePostListView view;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_post);
		
		intent = getIntent();
		store_id = intent.getIntExtra("store_id", 0);
		
		view = (StorePostListView) findViewById(R.id.store_post_list_view);
		view.setStoreId(store_id);
		view.setActivity(this);
		view.requestReload();
	}	
}