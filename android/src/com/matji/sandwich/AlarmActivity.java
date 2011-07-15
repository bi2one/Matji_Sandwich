package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.AlarmListView;
import com.matji.sandwich.widget.title.TitleText;

import android.os.Bundle;
import android.view.View;

public class AlarmActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		AlarmListView listView = (AlarmListView) findViewById(R.id.alarm_list);
		listView.setActivity(this);
		listView.requestReload();
	}
	
	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "AlarmActivity");
	}
}