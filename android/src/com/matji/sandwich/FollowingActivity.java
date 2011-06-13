package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.FollowingListView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class FollowingActivity extends BaseActivity {
	private Intent intent;
	private int user_id;
	private FollowingListType type;
	private FollowingListView listView;
	
	public enum FollowingListType {
		FOLLOWER, FOLLOWING
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_following);
		
		intent = getIntent();
		user_id = intent.getIntExtra("user_id", 0);
		type = (FollowingListType) intent.getSerializableExtra("type");
		
		listView = (FollowingListView) findViewById(R.id.following_activity_list);
		listView.setUserId(user_id);
		listView.setListType(type);
		listView.setActivity(this);
		listView.requestReload();
	}
	
	public void finish() {
		super.finishWithMatjiData();
	}
}