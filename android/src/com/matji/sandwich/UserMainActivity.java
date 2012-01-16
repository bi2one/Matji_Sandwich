package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.RoundTabHost;

public class UserMainActivity extends BaseTabActivity {
	public static final String USER = "UserMainActivity.user";
	public static User user;
	private RoundTabHost tabHost;

	public int setMainViewId() {
		return R.id.activity_user_main;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}

	private void init() {
		setContentView(R.layout.activity_user_main);
		user = getIntent().getParcelableExtra(USER);
		tabHost = (RoundTabHost) getTabHost();
		tabHost.addLeftTab("tab1",
				R.string.default_string_post, 
				new Intent(this, UserPostListActivity.class));
		tabHost.addCenterTab("tab2",
				R.string.default_string_picture,
				new Intent(this, UserImageListActivity.class));
		tabHost.addRightTab("tab3",
				R.string.default_string_webreview_user,
				new Intent(this, UserUrlListActivity.class));
	}

	public void refresh() {}

	@Override
	public void finish() {
		setResult(RESULT_OK);
		super.finish();
	}
}