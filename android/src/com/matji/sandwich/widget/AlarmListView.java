package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.CommentActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.AlarmAdapter;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.http.request.AlarmHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class AlarmListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;
	
	public AlarmListView(Context context, AttributeSet attrs) {
		super(context, attrs, new AlarmAdapter(context), 10);

		setPage(1);
	}

	public HttpRequest request() {
		if (request == null || !(request instanceof AlarmHttpRequest)) {
			request = new AlarmHttpRequest(getActivity());
		}

		((AlarmHttpRequest) request).actionList(getPage(), getLimit());

		return request;
	}
	
	public void onListItemClick(int position) {
		Alarm alarm = (Alarm) getAdapterData().get(position);
		String type = alarm.getAlarmType();

		if (type.equals("Comment") || type.equals("PostLike")) {
			Intent intent = new Intent(getActivity(), CommentActivity.class);
			intent.putExtra("post_id", alarm.getForeignKey());
			getActivity().startActivity(intent);
		} else if (type.equals("Following")) {
			if (alarm.getSentUser() != null) {
				Intent intent = new Intent(getActivity(), UserTabActivity.class);
				intent.putExtra(UserTabActivity.USER, (Parcelable) alarm.getSentUser());
				getActivity().startActivity(intent);
			}
		}
	}

	
	public void onClick(View v) {
		int position = Integer.parseInt((String) v.getTag());

		Alarm alarm = (Alarm) getAdapterData().get(position);

		if (alarm.getSentUser() != null) {
			Intent intent = new Intent(getActivity(), UserTabActivity.class);
			intent.putExtra(UserTabActivity.USER, (Parcelable) alarm.getSentUser());
			getActivity().startActivity(intent);
		}
	}
}