package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.cell.UserCell;

public class UserProfileActivity extends BaseActivity {
	private User user;
	
	public final String USER = "user";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void init() {
		setContentView(R.layout.activity_user_profile);
		user = (User) getIntent().getParcelableExtra(USER);
		((UserCell) findViewById(R.id.UserCell)).setUser(user);
	}
}
