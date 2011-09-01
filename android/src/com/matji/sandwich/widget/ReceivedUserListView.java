package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.SimpleUserAdapter;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;

public class ReceivedUserListView extends RequestableMListView {
	private HttpRequest request;
	
	public ReceivedUserListView(Context context, AttributeSet attrs) {
		super(context, attrs, new SimpleUserAdapter(context), 10);
		
		setPage(1);
	}
	
	@Override
	public HttpRequest request() {
		if (request == null || !(request instanceof FollowingHttpRequest)) {
			request = new FollowingHttpRequest(getContext());
		}
		
		((FollowingHttpRequest) request).actionFollowingList(Session.getInstance(getContext()).getCurrentUser().getId(), getPage(), getLimit());
		
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