package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.UrlAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UrlHttpRequest;

public class StoreUrlListView extends RequestableMListView {
	private HttpRequest request;
	private int store_id;

	public StoreUrlListView(Context context, AttributeSet attrs) {
		super(context, attrs, new UrlAdapter(context), 10);

		request = new UrlHttpRequest(context);
		setPage(1);
	}

	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	
	public HttpRequest request() {
		((UrlHttpRequest) request).actionStoreUrlList(store_id, getPage(), getLimit());
		
		return request;
	}

	public void onListItemClick(int position) {}
}