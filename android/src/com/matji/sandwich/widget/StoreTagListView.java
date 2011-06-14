package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.TagAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.TagHttpRequest;

public class StoreTagListView extends RequestableMListView {
	private HttpRequest request;
	private int store_id;

	public StoreTagListView(Context context, AttributeSet attrs) {
		super(context, attrs, new TagAdapter(context), 10);

		request = new TagHttpRequest(context);
		setPage(1);
	}

	public void setUserId(int store_id) {
		this.store_id = store_id;
	}
	
	public HttpRequest request() {
		((TagHttpRequest) request).actionStoreTagList(store_id, getPage(), getLimit());
		
		return request;
	}

	public void onListItemClick(int position) {}
}