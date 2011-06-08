package com.matji.sandwich.widget;

import android.app.TabActivity;
import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class StorePostListView extends PostListView {
	private HttpRequest request;
	private int store_id;	
	
	public StorePostListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		request = new PostHttpRequest(context);
		setPage(1);
	}

	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	
	@Override
	public HttpRequest request() {
		((PostHttpRequest) request).actionStoreList(store_id, getPage(), getLimit());
		return request;
	}

	@Override
	protected void gotoStorePage(Post post) {
		TabActivity act = (TabActivity) getActivity().getParent();
		act.getTabHost().setCurrentTab(StoreTabActivity.MAIN_PAGE);
	}
}
