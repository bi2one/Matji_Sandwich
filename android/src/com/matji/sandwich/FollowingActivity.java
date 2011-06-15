package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.FollowingListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FollowingActivity extends BaseActivity {
	private Intent intent;
	private int user_id;
	private FollowingListType type;
	private FollowingListView listView;
	
	public enum FollowingListType {
		FOLLOWER, FOLLOWING
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
	
	@Override
	protected String titleBarText() {
		return "FollowingActivity";
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