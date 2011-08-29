package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class UserPostListView extends PostListView {
	private HttpRequest request;
	private User user;

	public UserPostListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void init() {
		super.init();
		
		request = new PostHttpRequest(getContext());
		setPage(1);
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public HttpRequest request() {
		((PostHttpRequest) request).actionUserList(user.getId(), getPage(), getLimit());
		return request;
	}
}