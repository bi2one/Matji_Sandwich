package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.MessageThreadAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;

public class MessageListView extends RequestableMListView {
	protected HttpRequest request;

	public MessageListView(Context context, AttributeSet attrs) {
		super(context, attrs, new MessageThreadAdapter(context), 10);
		request = new MessageHttpRequest(context);
		
		setPage(1);
	}

	public HttpRequest request() {
		((MessageHttpRequest) request).actionList(getPage(), getLimit());
		return request;
	}

	public void onListItemClick(int position) {}
}
