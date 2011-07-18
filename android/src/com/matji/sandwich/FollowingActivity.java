package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.FollowingListView;

import android.os.Bundle;

public class FollowingActivity extends BaseActivity {
	private FollowingListView listView; 
	public enum FollowingListType {
		FOLLOWER, FOLLOWING
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_following);

		int user_id = getIntent().getIntExtra("user_id", 0);
		FollowingListType type = (FollowingListType) getIntent().getSerializableExtra("type");
		
		listView = (FollowingListView) findViewById(R.id.following_activity_list);
		listView.setUserId(user_id);
		listView.setListType(type);
		listView.setActivity(this);
		listView.requestReload();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		listView.dataRefresh();
	}
}