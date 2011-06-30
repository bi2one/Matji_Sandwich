package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WriteMessageActivity extends BaseActivity implements Requestable {
	private static final int MESSAGE_WRITE_REQUEST = 1;

	private HttpRequestManager manager;
	private HttpRequest messageHttpRequest;

	private EditText messageField;

	private int user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_comment);

		user_id = getIntent().getIntExtra("user_id", 0);
		messageField = (EditText) findViewById(R.id.comment_field);
		manager = HttpRequestManager.getInstance(getApplicationContext());
	}

	public void onMessageButtonClicked(View v) {
		messageHttpRequest = new MessageHttpRequest(getApplicationContext());
		if(messageField.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), R.string.default_string_writing_content, Toast.LENGTH_SHORT).show();
		} else {
			((MessageHttpRequest) messageHttpRequest).actionNew(user_id, messageField.getText().toString().trim());
			manager.request(this, messageHttpRequest, MESSAGE_WRITE_REQUEST, this);
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
		button.setText("Message");
		return true;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		onMessageButtonClicked(view);		
	}
}
