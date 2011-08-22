package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.FollowingListView;

public class FollowingActivity extends BaseActivity {
	private FollowingListView listView; 
	public enum FollowingListType {
		FOLLOWER, FOLLOWING
	}
	
	public static final String USER = "user";
	public static final String TYPE = "type";
	
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_following);

		user = getIntent().getParcelableExtra(USER);
		FollowingListType type = (FollowingListType) getIntent().getSerializableExtra(TYPE);
		
		listView = (FollowingListView) findViewById(R.id.following_activity_list);
		listView.setUser(user);
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