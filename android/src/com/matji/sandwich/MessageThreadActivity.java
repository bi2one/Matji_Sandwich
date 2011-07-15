package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.MessageThreadListView;
import com.matji.sandwich.widget.title.TitleButton;
import com.matji.sandwich.widget.title.TitleText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MessageThreadActivity extends BaseActivity {
	private MessageThreadListView listView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_thread);

		listView = (MessageThreadListView) findViewById(R.id.message_thread_list);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected void onResume() {
		super.onResume();
		listView.initItemVisible();
		listView.sort();
	}

	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "MessageThreadListActivity");
	}

	@Override
	protected View setRightTitleView() {
		return new TitleButton(this, "Message") {
			@Override
			public void onClick(View v) {
				onMessageButtonClicked();				
			}
		};
	}

	private void onMessageButtonClicked() {		
		if (loginRequired()) {
			Intent intent = new Intent(this, WriteMessageActivity.class);
			startActivity(intent);
		}
	}
}