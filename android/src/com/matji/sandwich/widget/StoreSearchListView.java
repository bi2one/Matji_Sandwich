//package com.matji.sandwich.widget;
//
//import com.matji.sandwich.Searchable;
//import com.matji.sandwich.http.request.HttpRequest;
//import com.matji.sandwich.http.request.StoreHttpRequest;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.util.Log;
//
//public class StoreSearchListView extends StoreListView implements Searchable {
//	private StoreHttpRequest storeRequest;
//	private String keyword = "";
//	
//	public StoreSearchListView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		storeRequest = new StoreHttpRequest(context);
//		addHeaderView(new SearchBar(context, this));
//	}
//	
//	@Override
//	public void search(String keyword) {
//		Log.d("refresh", "Search: " + keyword);
//		this.keyword = keyword;
//		requestReload();
//	}
//	
//	public HttpRequest request() {
//		storeRequest.actionSearch(keyword, getPage(), getLimit());
//		return storeRequest;
//	}
//}

package com.matji.sandwich.widget;

import com.matji.sandwich.Searchable;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class StoreSearchListView extends StoreListView implements Searchable {
	private StoreHttpRequest storeRequest;
	private String keyword = "";
	
	public StoreSearchListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		storeRequest = new StoreHttpRequest(context);
	}
	
	@Override
	public void search(String keyword) {
		Log.d("refresh", "Search: " + keyword);
		this.keyword = keyword;
		requestReload();
	}
	
	public HttpRequest request() {
		storeRequest.actionSearch(keyword, getPage(), getLimit());
		return storeRequest;
	}
}