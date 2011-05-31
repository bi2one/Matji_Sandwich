package com.matji.sandwich;

import java.util.*;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.session.Session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WritePostActivity extends Activity implements Requestable {
	HttpRequestManager manager;
	private PostHttpRequest postHttpRequest;
	private Intent intent;
	EditText postField;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_post);
		postField = (EditText) findViewById(R.id.post_field);
		manager = new HttpRequestManager(getApplicationContext(), this);

	}

	public void imageButtonClicked(View v) {

	}

	public void postButtonClicked(View v) {
		postHttpRequest = new PostHttpRequest(getApplicationContext());
		postHttpRequest.actionNew(postField.getText().toString());
		manager.request(this, postHttpRequest, 1);
	}
	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getApplicationContext());
	}

}
