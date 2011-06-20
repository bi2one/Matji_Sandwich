package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.UserStoreListView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserStoreListActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_store);

		int user_id = getIntent().getIntExtra("user_id", 0);

		UserStoreListView listView = (UserStoreListView) findViewById(R.id.user_store_list);
		listView.setUserId(user_id);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected String titleBarText() {
		return "UserStoreListActivity";
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