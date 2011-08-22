package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.FollowingActivity.FollowingListType;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class FollowingListView extends SimpleUserListView {
	private HttpRequest request;
	private User user;
	private FollowingListType listType;
	
	public FollowingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		request = new FollowingHttpRequest(context);

		setPage(1);
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setListType(FollowingListType type) {
		this.listType = type;
	}

	public HttpRequest request() {
		switch(listType) {
		case FOLLOWER:
			((FollowingHttpRequest) request).actionFollowerList(user.getId(), getPage(), getLimit());
			break;
		case FOLLOWING:
			((FollowingHttpRequest) request).actionFollowingList(user.getId(), getPage(), getLimit());
			break;
		}
		return request;
	}
}