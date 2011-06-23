package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.ChatActivity;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.session.Session;

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
		
		User me = Session.getInstance(context).getCurrentUser();
		int user_id = (message.getSentUserId() == me.getId()) ? message.getReceivedUserId() : message.getSentUserId();
		
		Intent intent = new Intent(context, ChatActivity.class);
		intent.putExtra("thread_id", message.getThreadId());
		intent.putExtra("user_id", user_id);
		context.startActivity(intent);
	}

	public void onClick(View v) {}
}