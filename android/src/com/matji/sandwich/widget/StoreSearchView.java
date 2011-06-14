package com.matji.sandwich.widget;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;

import android.content.Context;
import android.util.AttributeSet;

public class StoreSearchView extends StoreListView {
	private StoreHttpRequest storeRequest;
	private String keyword = "완차이";
	
	public StoreSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		storeRequest = new StoreHttpRequest(context);
	}
	
	public HttpRequest request() {
		storeRequest.actionSearch(keyword, getPage(), getLimit());
		return storeRequest;
	}
}
