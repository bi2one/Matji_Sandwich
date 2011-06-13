package com.matji.sandwich.widget;


import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class MyPostListView extends PostListView {
	private PostHttpRequest postRequest;
	private int userId;
	
	public MyPostListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		postRequest = new PostHttpRequest(context);
	}

	public HttpRequest request() {
		postRequest.actionMyList(getPage(), getLimit());
		return postRequest;
	}

	public int getUserId() {
		return this.userId;
	}
	
	public void setUserId(int userid) {
		this.userId = userid;
	}
}
