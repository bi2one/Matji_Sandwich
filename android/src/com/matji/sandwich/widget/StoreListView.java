package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.StoreAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class StoreListView extends RequestableMListView {
	private HttpRequest request;

	public StoreListView(Context context, AttributeSet attrs) {
		super(context, attrs, new StoreAdapter(context), 10);

		request = new StoreHttpRequest(context);
		setPage(1);
	}

	public HttpRequest request() {
		((StoreHttpRequest) request).actionList(getPage(), getLimit());
		return request;
	}

	public void onListItemClick(int position) {
		Store store = (Store) getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), StoreMainActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, store);
	}
}