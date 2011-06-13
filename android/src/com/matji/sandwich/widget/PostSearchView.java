package com.matji.sandwich.widget;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

import android.content.Context;
import android.util.AttributeSet;

public class PostSearchView extends PostListView {
	private PostHttpRequest postRequest;
	private String keyword = "test";
	
	public PostSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		postRequest = new PostHttpRequest(context);
	}
	
	public HttpRequest request() {
		postRequest.actionSearch(keyword, getPage(), getLimit());
		return postRequest;
	}
}
