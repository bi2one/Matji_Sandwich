package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.ReceivedUserListView;
import com.matji.sandwich.widget.title.TitleText;

import android.os.Bundle;
import android.view.View;

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
	protected View setCenterTitleView() {
		return new TitleText(this, "ReceivedUserActivity");
	}
}