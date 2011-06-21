package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.AlarmAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.http.request.AlarmHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class AlarmListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;
	private Context context;
	
	public AlarmListView(Context context, AttributeSet attrs) {
		super(context, attrs, new AlarmAdapter(context), 10);
		this.context = context;
		
		request = new AlarmHttpRequest(context);
		setPage(1);
	}

	public HttpRequest request() {
		((AlarmHttpRequest) request).actionList(getPage(), getLimit());
		
		return request;
	}

	public void onListItemClick(int position) {}

	@Override
	public void onClick(View v) {
		int position = Integer.parseInt((String) v.getTag());
		
		Alarm alarm = (Alarm) getAdapterData().get(position);
		
		if (alarm.getSentUser() != null) {
			Intent intent = new Intent(context, UserTabActivity.class);
			((BaseActivity) context).startActivityWithMatjiData(intent, alarm.getSentUser());
		}
	}
}