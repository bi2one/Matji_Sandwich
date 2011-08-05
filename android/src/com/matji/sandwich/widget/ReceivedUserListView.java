package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.ReceivedUserADapter;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;

public class ReceivedUserListView extends RequestableMListView {
	private Context context;
	private HttpRequest request;
	private int user_id;
	
	public ReceivedUserListView(Context context, AttributeSet attrs) {
		super(context, attrs, new ReceivedUserADapter(context), 10);
		this.context = context;
		
		user_id = Session.getInstance(context).getCurrentUser().getId();
		
		setPage(1);
	}
	
	@Override
	public HttpRequest request() {
		if (request == null || !(request instanceof FollowingHttpRequest)) {
			request = new FollowingHttpRequest(context);
		}
		((FollowingHttpRequest) request).actionList(user_id, getPage(), getLimit());
		
		return request;
	}
	
	@Override
	public void onListItemClick(int position) {
		User user = (User) getAdapterData().get(position);
		Intent intent = new Intent();
		intent.putExtra("user", (Parcelable) user);
		getActivity().setResult(Activity.RESULT_OK, intent);
		getActivity().finish();
	}
}