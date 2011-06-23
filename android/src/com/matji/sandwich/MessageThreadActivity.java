package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.MessageThreadListView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MessageThreadActivity extends BaseActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_thread);

		MessageThreadListView listView = (MessageThreadListView) findViewById(R.id.message_thread_list);
		listView.setActivity(this);
		listView.requestReload();
	}
	
	@Override
	protected String titleBarText() {
		return "MessageThreadListActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
	}
}