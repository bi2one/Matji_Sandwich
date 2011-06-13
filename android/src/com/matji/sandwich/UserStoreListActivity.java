package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.UserStoreListView;

import android.content.Intent;
import android.os.Bundle;

public class UserStoreListActivity extends BaseActivity {
	private Intent intent;
	private int user_id;		
	private UserStoreListView listView;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_store);
		
		intent = getIntent();
		user_id = intent.getIntExtra("user_id", 0);
		
		listView = (UserStoreListView) findViewById(R.id.user_store_list);
		listView.setUserId(user_id);
		listView.setActivity(this);
		listView.requestReload();
	}
}