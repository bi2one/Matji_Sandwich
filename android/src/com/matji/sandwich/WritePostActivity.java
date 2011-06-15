package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.PostHttpRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WritePostActivity extends BaseActivity implements Requestable {
	private static final int POST_WRITE_REQUEST = 1;
	HttpRequestManager manager;
	private PostHttpRequest postHttpRequest;
	EditText postField;
	EditText tagsField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_post);
		
		postField = (EditText) findViewById(R.id.post_field);
		tagsField = (EditText) findViewById(R.id.tags_field);
		manager = new HttpRequestManager(getApplicationContext(), this);
	}
	
	@Override
	protected String usedTitleBar() {
		// TODO
		return "WritePostActivity";
	}
	
	public void imgButtonClicked(View v) {
		
	}
	
	public void mapButtonClicked(View v) {
		
	}

	public void postButtonClicked(View v) {
		postHttpRequest = new PostHttpRequest(getApplicationContext());
		if(postField.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Writing Content!", Toast.LENGTH_SHORT).show();
		} else {
			if(!tagsField.getText().toString().trim().equals("")) {
				postHttpRequest.actionNew(postField.getText().toString().trim()
						,tagsField.getText().toString().trim());							
			} else {
				postHttpRequest.actionNew(postField.getText().toString().trim(),"");
			}
		}
		manager.request(this, postHttpRequest, POST_WRITE_REQUEST);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch(tag) {
			case POST_WRITE_REQUEST:
				setResult(RESULT_OK);
				finish();
				break;
		}
	}
	
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getApplicationContext());
	}
}
