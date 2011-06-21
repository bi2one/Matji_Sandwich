package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.PostMainActivity;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.AlarmAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AlarmHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class AlarmListView extends RequestableMListView implements View.OnClickListener, Requestable {
	private HttpRequestManager manager;
	private HttpRequest request;
	
	private static final int POST_REQUEST = 11; 

	public AlarmListView(Context context, AttributeSet attrs) {
		super(context, attrs, new AlarmAdapter(context), 10);
		manager = new HttpRequestManager(context, this);

		setPage(1);
	}

	public HttpRequest request() {
		if (request == null || !(request instanceof AlarmHttpRequest)) {
			request = new AlarmHttpRequest(getActivity());
		}

		((AlarmHttpRequest) request).actionList(getPage(), getLimit());

		return request;
	}

	private HttpRequest postRequest(int post_id) {
		if (request == null || !(request instanceof PostHttpRequest)) { 
			request = new PostHttpRequest(getActivity());
		}
		
		((PostHttpRequest) request).actionShow(post_id);
		
		return request;
	}
	
	public void onListItemClick(int position) {
		Alarm alarm = (Alarm) getAdapterData().get(position);
		String type = alarm.getAlarmType();

		if (type.equals("Comment") || type.equals("PostLike")) {
			alarm.getForeignKey();
			manager.request(getActivity(), postRequest(alarm.getForeignKey()), POST_REQUEST);
		}
	}

	@Override
	public void onClick(View v) {
		int position = Integer.parseInt((String) v.getTag());

		Alarm alarm = (Alarm) getAdapterData().get(position);

		if (alarm.getSentUser() != null) {
			Intent intent = new Intent(getActivity(), UserTabActivity.class);
			((BaseActivity) getActivity()).startActivityWithMatjiData(intent, alarm.getSentUser());
		}
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub
		switch (tag) {
		case REQUEST_NEXT: case REQUEST_RELOAD:
			super.requestCallBack(tag, data);
			break;
		case POST_REQUEST:
			if (data != null) {
				Post post = (Post) data.get(0);
				Intent intent = new Intent(getActivity(), PostMainActivity.class);
				((BaseActivity) getActivity()).startActivityWithMatjiData(intent, post);
			}
			break;
		}
	}
}