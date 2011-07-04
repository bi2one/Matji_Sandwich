package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.MessageThreadAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.session.Session;

public class MessageListView extends RequestableMListView {
	protected Session session;
	protected HttpRequest request;
	protected Context context;

	public MessageListView(Context context, AttributeSet attrs) {
		super(context, attrs, new MessageThreadAdapter(context), 10);
		this.context = context;
		session = Session.getInstance(context);
		
		setPage(1);
	}

	public HttpRequest request() {
		((MessageHttpRequest) request).actionList(getPage(), getLimit());
		return request;
	}

	public void onListItemClick(int position) {}
	
	protected void gotoUserPage(Message message) {
		User user = (message.getReceivedUserId() == session.getCurrentUser().getId()) ? message.getSentUser() : message.getReceivedUser();
		Intent intent = new Intent(getActivity(), UserTabActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, user);
	}	

}
