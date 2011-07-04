package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.ChatView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChatActivity extends BaseActivity implements Requestable {
	private HttpRequestManager manager;
	
	private Message message;
	private int user_id;
	private ChatView listView;

	private final int MESSAGE_NEW = 10;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		manager = HttpRequestManager.getInstance(this);
		message = (Message) SharedMatjiData.getInstance().top();
		User me = Session.getInstance(this).getCurrentUser();
		user_id = (message.getSentUserId() == me.getId()) ? message.getReceivedUserId() : message.getSentUserId();
		listView = (ChatView) findViewById(R.id.chat);
		listView.setThreadId(message.getThreadId());
		listView.setActivity(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		listView.requestReload();
	}
	
	@Override
	public void finish() {
		super.finishWithMatjiData();
	}
	
	@Override
	protected String titleBarText() {
		return "ChatActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		button.setText("Refresh");
		return true;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		if (!listView.getManager().isRunning(this)) {
			listView.requestReload();
		}
	}

	public void onSendButtonClicked(View view) {
		EditText messageField = (EditText) findViewById(R.id.chat_message_field);

		String message =  messageField.getText().toString().trim();
		
		if(message.equals("")) {
			Toast.makeText(getApplicationContext(), R.string.writing_content_message, Toast.LENGTH_SHORT).show();
		} else {
		    manager.request(this, request(message), MESSAGE_NEW, this);
			
			/* keyboard hide. */
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);  
			imm.hideSoftInputFromWindow(messageField.getWindowToken(), 0); 

			/* init edit text string field. */
			messageField.setText("");
		}
	}

	private HttpRequest request(String message) {
		MessageHttpRequest request = new MessageHttpRequest(this);
		request.actionNew(user_id, message);

		return request;
	}
	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case MESSAGE_NEW:
			Message recentMessage = (Message) data.get(0);
			message.setMessage(recentMessage.getMessage());
			message.setAgo(recentMessage.getAgo());
			listView.addMessage(recentMessage);
			break;
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
	}
}