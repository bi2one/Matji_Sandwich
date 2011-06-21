package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WriteCommentActivity extends BaseActivity implements Requestable {
	private static final int COMMENT_WRITE_REQUEST = 1;

	private HttpRequestManager manager;
	private HttpRequest commentHttpRequest;
	private Session session;

	private EditText commentField;
	
	private int post_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_comment);
		
		post_id = getIntent().getIntExtra("post_id", 0);
		commentField = (EditText) findViewById(R.id.post_field);
		manager = new HttpRequestManager(getApplicationContext(), this);
		session = Session.getInstance(this);
	}
	
	public void onPostButtonClicked(View v) {
		commentHttpRequest = new CommentHttpRequest(getApplicationContext());
		if(commentField.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Writing Content!", Toast.LENGTH_SHORT).show();
		} else {
			if (post_id != 0) {
				((CommentHttpRequest) commentHttpRequest).actionNew(0, commentField.getText().toString().trim(), "ANDROID");
			}
		}
		
		manager.request(this, commentHttpRequest, COMMENT_WRITE_REQUEST);
		User me = session.getCurrentUser();
		me.setPostCount(me.getPostCount() + 1);
	}
	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch(tag) {
			case COMMENT_WRITE_REQUEST:
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
		return "WritePostActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		
	}
}
