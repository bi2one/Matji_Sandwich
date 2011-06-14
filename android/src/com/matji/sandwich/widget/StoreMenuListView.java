package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.FoodAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreFoodHttpRequest;

public class StoreMenuListView extends RequestableMListView {
	private HttpRequest request;
	private int store_id;

	public StoreMenuListView(Context context, AttributeSet attrs) {
		super(context, attrs, new FoodAdapter(context), 10);

		request = new StoreFoodHttpRequest(context);
		setPage(1);
	}

	public void setUserId(int store_id) {
		this.store_id = store_id;
	}
	
	public HttpRequest request() {
		((StoreFoodHttpRequest) request).actionList(store_id, getPage(), getLimit());
		
		return request;
	}

	public void onListItemClick(int position) {}
}