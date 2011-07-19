package com.matji.sandwich.widget;

import com.matji.sandwich.Searchable;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class PostSearchView extends PostListView implements Searchable {
	private PostHttpRequest postRequest;
	private String keyword = "";
	
	public PostSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		postRequest = new PostHttpRequest(context);
	}
	
	public HttpRequest request() {
		postRequest.actionSearch(keyword, getPage(), getLimit());
		return postRequest;
	}

	
	public void search(String keyword) {
		Log.d("refresh", "Search: " + keyword);
		this.keyword = keyword;
		requestReload();
	}
}