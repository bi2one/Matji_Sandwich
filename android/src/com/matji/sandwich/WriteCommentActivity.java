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
import android.widget.EditText;
import android.widget.Toast;

public class WriteCommentActivity extends BaseActivity implements Requestable {
	private static final int COMMENT_WRITE_REQUEST = 1;

	private HttpRequestManager manager;
	private HttpRequest commentHttpRequest;

	private EditText commentField;

	private int post_id;

    public int setMainViewId() {
	return R.id.contentWrapper;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_comment);

		post_id = getIntent().getIntExtra("post_id", 0);
		commentField = (EditText) findViewById(R.id.comment_field);
		manager = HttpRequestManager.getInstance(getApplicationContext());
	}

	public void onCommentButtonClicked(View v) {
		commentHttpRequest = new CommentHttpRequest(getApplicationContext());
		if(commentField.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), R.string.writing_content_comment, Toast.LENGTH_SHORT).show();
		} else {
			((CommentHttpRequest) commentHttpRequest).actionNew(post_id, commentField.getText().toString().trim(), "ANDROID");
			manager.request(getMainView(), commentHttpRequest, COMMENT_WRITE_REQUEST, this);
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
}
