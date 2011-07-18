package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.UserPostListView;

import android.content.Intent;
import android.os.Bundle;

public class UserPostListActivity extends BaseActivity {
	private Intent intent;
	private int user_id;		
	private UserPostListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_post);
		
		intent = getIntent();
		user_id = intent.getIntExtra("user_id", 0);
		
		listView = (UserPostListView) findViewById(R.id.user_post_list_view);
		listView.setUserId(user_id);
		listView.setActivity(this);
		listView.requestReload();
	}
	@Override
	protected void onResume() {
		super.onResume();
		listView.dataRefresh();
	}
}