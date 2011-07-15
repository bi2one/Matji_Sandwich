package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.NoticeListView;
import com.matji.sandwich.widget.title.TitleText;

import android.os.Bundle;
import android.view.View;

public class NoticeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);

		NoticeListView listView = (NoticeListView) findViewById(R.id.notice_list);
		listView.setActivity(this);
		listView.requestReload();
	}
	
	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "NoticeActivity");
	}
}
