package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WriteCommentActivity extends BaseActivity implements Requestable {
	private static final int COMMENT_WRITE_REQUEST = 1;

	private HttpRequestManager manager;
	private HttpRequest commentHttpRequest;

	private EditText commentField;

	private int post_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_comment);

		post_id = getIntent().getIntExtra("post_id", 0);
		commentField = (EditText) findViewById(R.id.comment_field);
		manager = new HttpRequestManager(getApplicationContext(), this);
	}

	public void onCommentButtonClicked(View v) {
		commentHttpRequest = new CommentHttpRequest(getApplicationContext());
		if(commentField.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), R.string.default_string_writing_content, Toast.LENGTH_SHORT).show();
		} else {
			((CommentHttpRequest) commentHttpRequest).actionNew(post_id, commentField.getText().toString().trim(), "ANDROID");
			manager.request(this, commentHttpRequest, COMMENT_WRITE_REQUEST);
		}
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch(tag) {
		case COMMENT_WRITE_REQUEST:
			Intent intent = new Intent();
			if (data != null) intent.putExtra("comment", (Comment) data.get(0));
			setResult(RESULT_OK, intent);
			finish();
			break;
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getApplicationContext());
	}

	@Override
	protected String titleBarText() {
		return "WritePostActivity";
	}

	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		button.setText("Send");
		return true;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		onCommentButtonClicked(view);
	}
}
