package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.UserPostListView;

import android.os.Bundle;

public class UserPostListActivity extends BaseActivity {
	private User user;		
	private UserPostListView listView;
	
	public static final String USER = "user";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_post);
		
		user = getIntent().getParcelableExtra(USER);
		
		listView = (UserPostListView) findViewById(R.id.user_post_list_view);
		listView.setUser(user);
		listView.setActivity(this);
		listView.requestReload();
	}
	@Override
	protected void onResume() {
		super.onResume();
		listView.dataRefresh();
	}
}