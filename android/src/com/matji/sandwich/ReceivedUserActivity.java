package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.ReceivedUserListView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReceivedUserActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_received_user);

		ReceivedUserListView listView = (ReceivedUserListView) findViewById(R.id.received_user_list);
		listView.setActivity(this);
		listView.requestReload();
	}
	
	@Override
	protected String titleBarText() {
		return "ReceivedUserActivity";
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