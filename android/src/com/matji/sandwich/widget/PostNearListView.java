package com.matji.sandwich.widget;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

import android.content.Context;
import android.util.AttributeSet;

public class PostNearListView extends PostListView{
	private PostHttpRequest postRequest;
	private double lat_sw=37.3;
	private double lat_ne=126.804;
	private double lng_sw=37.4;
	private double lng_ne=126.828;

	public PostNearListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		postRequest = new PostHttpRequest(context);
	}

	@Override
	public HttpRequest request() {
		postRequest.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, getPage(), getLimit());
		return postRequest;
	}
}
