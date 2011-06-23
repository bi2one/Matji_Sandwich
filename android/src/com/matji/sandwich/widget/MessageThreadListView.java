package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.ChatActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;

public class MessageThreadListView extends MessageListView implements View.OnClickListener {
	private Context context;
	
	public MessageThreadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public HttpRequest request() {
		((MessageHttpRequest) request).actionThreadList(getPage(), getLimit());
		return request;
	}

	public void onListItemClick(int position) {
		Message message = (Message) getAdapterData().get(position);
		((BaseActivity) context).startActivityWithMatjiData(new Intent(context, ChatActivity.class), message);
	}

	public void onClick(View v) {}
}