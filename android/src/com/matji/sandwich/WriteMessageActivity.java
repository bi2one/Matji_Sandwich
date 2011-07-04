package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WriteMessageActivity extends BaseActivity implements Requestable {
	private static final int MESSAGE_WRITE_REQUEST = 11;
//	private static final int  = 12;
	
	private HttpRequestManager manager;
	private HttpRequest request;

	private EditText messageField;
	private EditText receivedUserField;
	
	private int user_id;
	
	private static final int USER_ID_IS_NULL = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_message);

		manager = HttpRequestManager.getInstance(getApplicationContext());
		
		user_id = getIntent().getIntExtra("user_id", USER_ID_IS_NULL);
		
		messageField = (EditText) findViewById(R.id.write_message_message_field);
		receivedUserField = (EditText) findViewById(R.id.write_message_received_user_field);
		receivedUserField.setFocusable(false);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		messageField.requestFocus();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RECEIVED_USER_ACTIVITY:
			if (resultCode == RESULT_OK) {
				if (data != null) {
					User user = (User) data.getParcelableExtra("user");
					if (user != null) {
						receivedUserField.setText(user.getNick());
						user_id = user.getId();
					}
				}
			}
			break;
		}
	}
	
	public HttpRequest sendRequest() {
		if (request == null || !(request instanceof MessageHttpRequest)) {
			request = new MessageHttpRequest(getApplicationContext());
		}
		((MessageHttpRequest) request).actionNew(user_id, messageField.getText().toString().trim());
		
		return request;
	}

	public void onAddButtonClicked(View v) {
		Intent intent = new Intent(this, ReceivedUserActivity.class);
		startActivityForResult(intent, RECEIVED_USER_ACTIVITY);
	}

	public void onSendButtonClicked(View v) {
		if (messageField.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), R.string.writing_content_message, Toast.LENGTH_SHORT).show();
		} else if (user_id == USER_ID_IS_NULL) {
			Toast.makeText(getApplicationContext(), R.string.writing_content_received_user, Toast.LENGTH_SHORT).show();
		} else {
			manager.request(this, sendRequest(), MESSAGE_WRITE_REQUEST, this);
		}
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch(tag) {
		case MESSAGE_WRITE_REQUEST:
			setResult(RESULT_OK);
			finish();
			break;
		
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getApplicationContext());
	}

	@Override
	protected String titleBarText() {
		return "WriteMessageActivity";
	}

	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		button.setText("Send");
		return true;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		onSendButtonClicked(view);		
	}
}

//package com.matji.sandwich;
//
//import java.util.ArrayList;
//
//import com.matji.sandwich.base.BaseActivity;
//import com.matji.sandwich.data.MatjiData;
//import com.matji.sandwich.exception.MatjiException;
//import com.matji.sandwich.http.HttpRequestManager;
//import com.matji.sandwich.http.request.FollowingHttpRequest;
//import com.matji.sandwich.http.request.MessageHttpRequest;
//import com.matji.sandwich.http.request.HttpRequest;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public class WriteMessageActivity extends BaseActivity implements Requestable {
//	private static final int MESSAGE_WRITE_REQUEST = 11;
////	private static final int  = 12;
//
//	private HttpRequestManager manager;
//	private HttpRequest request;
//
//	private EditText messageField;
//
//	private ArrayList<Integer> userIds;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_write_message);
//
//		manager = HttpRequestManager.getInstance(getApplicationContext());
//		
//		userIds = new ArrayList<Integer>();
//		int user_id = getIntent().getIntExtra("user_id", -1);
//		if (!(user_id == -1)) {
//			userIds.add(user_id);
//		}
//		
//		messageField = (EditText) findViewById(R.id.write_message_message_field);
//		findViewById(R.id.write_message_received_user_field).setFocusable(false);
//	}
//	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		messageField.requestFocus();
//	}
//	
//	public HttpRequest sendRequest() {
//		if (request == null || !(request instanceof MessageHttpRequest)) {
//			request = new MessageHttpRequest(getApplicationContext());
//		}
//		((MessageHttpRequest) request).actionNew(user_id, messageField.getText().toString().trim());
//		
//		return request;
//	}
//
//	public HttpRequest followingRequest() {
//		if (request == null || !(request instanceof FollowingHttpRequest)) {
//			request = new FollowingHttpRequest(getApplicationContext());
//		}
//		((FollowingHttpRequest) request).actionList(user_id);
//		
//		return request;		
//	}
//	
//	public void onAddButtonClicked(View v) {
//		
//	}
//
//	public void onSendButtonClicked(View v) {
//		if (messageField.getText().toString().trim().equals("")) {
//			Toast.makeText(getApplicationContext(), R.string.writing_content_message, Toast.LENGTH_SHORT).show();
//		} else if (user_id == USER_ID_IS_NULL) {
//			Toast.makeText(getApplicationContext(), R.string.writing_content_received_user, Toast.LENGTH_SHORT).show();
//		} else {
//			manager.request(this, sendRequest(), MESSAGE_WRITE_REQUEST, this);
//		}
//	}
//
//	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
//		switch(tag) {
//		case MESSAGE_WRITE_REQUEST:
//			setResult(RESULT_OK);
//			finish();
//			break;
//		
//		}
//	}
//
//	public void requestExceptionCallBack(int tag, MatjiException e) {
//		e.performExceptionHandling(getApplicationContext());
//	}
//
//	@Override
//	protected String titleBarText() {
//		return "WriteMessageActivity";
//	}
//
//	protected boolean setTitleBarButton(Button button) {
//		// TODO Auto-generated method stub
//		button.setText("Send");
//		return true;
//	}
//
//	@Override
//	protected void onTitleBarItemClicked(View view) {
//		// TODO Auto-generated method stub
//		onSendButtonClicked(view);		
//	}
//}