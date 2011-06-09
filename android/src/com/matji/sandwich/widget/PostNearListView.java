package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class PostNearListView extends PostListView {
	
	private PostHttpRequest postRequest;
	private double lat_sw=37.3;
	private double lat_ne=126.1;
	private double lng_sw=37.6;
	private double lng_ne=126.9;

	public PostNearListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		postRequest = new PostHttpRequest(context);
	}

	public HttpRequest request() {
		postRequest.actionNearbyList(lat_ne, lat_sw, lng_sw, lng_ne, getPage(), getLimit());
		return postRequest;
	}
	
}
