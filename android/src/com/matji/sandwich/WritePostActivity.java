package com.matji.sandwich;

import android.os.Bundle;
import android.content.Context;
import android.util.Log;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.title.CompletableTitle;

public class WritePostActivity extends BaseActivity implements CompletableTitle.Completable {
    private Context context;
    private CompletableTitle titleBar;

    public int setMainViewId() {
	return R.id.activity_write_post;
    }
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_write_post);
	
	context = getApplicationContext();
	titleBar = (CompletableTitle)findViewById(R.id.activity_write_post_title);
	titleBar.setTitle(R.string.write_post_activity_title);
    }

    protected void onResume() {
	super.onResume();
    }

    protected void onPause() {
	super.onPause();
	// sessionUtil.setPost(editText.getText().toString());
	// parentActivity.hideKeyboard();
    }

    public void complete() {
	Log.d("=====", "complete");
    }
}