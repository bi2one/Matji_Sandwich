package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.AlarmListView;

import android.os.Bundle;

public class AlarmActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		AlarmListView listView = (AlarmListView) findViewById(R.id.alarm_list);
		listView.setActivity(this);
		listView.requestReload();
	}
}