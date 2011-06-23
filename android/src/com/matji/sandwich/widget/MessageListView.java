package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.adapter.MessageAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;

public class MessageListView extends RequestableMListView implements View.OnClickListener {
	protected HttpRequest request;

	public MessageListView(Context context, AttributeSet attrs) {
		super(context, attrs, new MessageAdapter(context), 10);
		request = new MessageHttpRequest(context);
		
		setPage(1);
	}

	public HttpRequest request() {
		((MessageHttpRequest) request).actionList(getPage(), getLimit());
		return request;
	}

	public void onListItemClick(int position) {}

	public void onClick(View v) {}
}
