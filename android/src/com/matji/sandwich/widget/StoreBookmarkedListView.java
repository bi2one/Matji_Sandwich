package com.matji.sandwich.widget;

import android.content.Context;

import android.util.AttributeSet;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;

public class StoreBookmarkedListView extends StoreListView {
	private StoreHttpRequest storeRequest;
	private int userId;
	
	public StoreBookmarkedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		storeRequest = new StoreHttpRequest(context);
	}
	
	
	public void setUserId(int userid){
		this.userId = userid;
	}

	public int getUserId(){
		return this.userId;
	}
	
	public HttpRequest request() {
		storeRequest.actionBookmarkedList(userId, getPage(), getLimit());
		return storeRequest; 
	}
}
