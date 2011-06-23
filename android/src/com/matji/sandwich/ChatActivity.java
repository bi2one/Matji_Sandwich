package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
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

	private int user_id;
	private ChatView listView;

	private final int MESSAGE_NEW = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		manager = new HttpRequestManager(this, this);

		int thread_id = getIntent().getIntExtra("thread_id", -1);
		user_id = getIntent().getIntExtra("user_id", 0);
		listView = (ChatView) findViewById(R.id.chat);
		listView.setThreadId(thread_id);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected String titleBarText() {
		return "MessageThreadListActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
	}

	public void onSendButtonClicked(View view) {
		EditText messageField = (EditText) findViewById(R.id.chat_message_field);

		String message =  messageField.getText().toString().trim();
		
		if(message.equals("")) {
			Toast.makeText(getApplicationContext(), R.string.default_string_writing_content, Toast.LENGTH_SHORT).show();
		} else {
			manager.request(this, request(message), MESSAGE_NEW);
			
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

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case MESSAGE_NEW:
			if (data != null) listView.addMessage((Message) data.get(0));
			break;
		}
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
	}
}