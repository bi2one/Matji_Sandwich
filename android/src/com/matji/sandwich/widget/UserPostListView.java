package com.matji.sandwich.widget;

import android.app.TabActivity;
import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class UserPostListView extends PostListView {
	private HttpRequest request;
	private int user_id;

	public UserPostListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		request = new PostHttpRequest(context);
		setPage(1);
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public HttpRequest request() {
		((PostHttpRequest) request).actionUserList(user_id, getPage(), getLimit());
		return request;
	}
	
	@Override
	protected void gotoUserPage(int position) {
		TabActivity act = (TabActivity) getActivity().getParent();
		act.getTabHost().setCurrentTab(0);
	}
}