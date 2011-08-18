package com.matji.sandwich;

import android.os.Bundle;
import android.widget.EditText;
import android.content.Context;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.SessionWritePostUtil;

public class WritePostActivity extends BaseActivity {
    private Context context;
    private EditText editText;
    private SessionWritePostUtil sessionUtil;
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_write_post);
	context = getApplicationContext();
	editText = (EditText)findViewById(R.id.activity_write_post_text);
	sessionUtil = new SessionWritePostUtil(context);
    }

    protected void onResume() {
	super.onResume();
    }

    protected void onPause() {
	super.onPause();
	sessionUtil.setPost(editText.getText().toString());
    }
}