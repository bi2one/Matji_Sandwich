package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

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
	public void onListItemClick(int position) {
		Log.d("Matji", "UserPostListView:: OnListItemClick!");
	}

	@Override
	protected void gotoUserPage(int position){}
}
