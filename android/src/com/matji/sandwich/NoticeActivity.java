package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.NoticeListView;

import android.os.Bundle;

public class NoticeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);

		NoticeListView listView = (NoticeListView) findViewById(R.id.notice_list);
		listView.setActivity(this);
		listView.requestReload();
	}
}
