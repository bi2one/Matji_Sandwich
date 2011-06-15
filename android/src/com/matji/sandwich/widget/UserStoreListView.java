package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.StoreAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class UserStoreListView extends RequestableMListView {
	private HttpRequest request;
	private int user_id;

	public UserStoreListView(Context context, AttributeSet attrs) {
		super(context, attrs, new StoreAdapter(context), 10);

		request = new StoreHttpRequest(context);
		setPage(1);
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	public HttpRequest request() {
		((StoreHttpRequest) request).actionBookmarkedList(user_id, getPage(), getLimit());
		
		return request;
	}

	public void onListItemClick(int position) {
		Store store = (Store) getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), StoreTabActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, store);		
	}
}