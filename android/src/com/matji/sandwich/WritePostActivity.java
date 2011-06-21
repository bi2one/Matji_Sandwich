package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
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

public class WritePostActivity extends BaseActivity implements Requestable, RelativeLayoutThatDetectsSoftKeyboard.Listener {
	private static final int POST_WRITE_REQUEST = 1;
	HttpRequestManager manager;
	private PostHttpRequest postHttpRequest;
	private Session session;
	private int keyboardHeight;
	private int contentHeightWithoutKeyboard;
	private int contentHeightWithKeyboard;
	private EditText postField;
	private EditText tagsField;
	private RelativeLayoutThatDetectsSoftKeyboard contentWrapper;
	private LinearLayout postFooterContentLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_post);
		
		postField = (EditText) findViewById(R.id.post_field);
		tagsField = (EditText) findViewById(R.id.tags_field);
		manager = new HttpRequestManager(getApplicationContext(), this);
		session = Session.getInstance(this);
		
				
		contentWrapper = (RelativeLayoutThatDetectsSoftKeyboard)findViewById(R.id.contentWrapper);
		contentWrapper.setListener(this);
		
		postFooterContentLayout = (LinearLayout)findViewById(R.id.post_footer_content);

//		View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
//		    public void onFocusChange(View v, boolean hasFocus) {
//		        if (hasFocus) {
//		        	getWindow().
//		        	
//		        }
//		    }
//		};
//		
//		postField.setOnFocusChangeListener(listener);
//		tagsField.setOnFocusChangeListener(listener);
	}
	
	
	
	private void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);	
	}
	
	private void showKeyboard(View view){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}
	
	public void onImgButtonClicked(View v) {
		hideKeyboard();
	}
	
	
	public void onMapButtonClicked(View v) {
		hideKeyboard();
	}

	public void onPostButtonClicked(View v) {
		postHttpRequest = new PostHttpRequest(getApplicationContext());
		if(postField.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Writing Content!", Toast.LENGTH_SHORT).show();
		} else {
			if(!tagsField.getText().toString().trim().equals("")) {
				postHttpRequest.actionNew(postField.getText().toString().trim()
						,tagsField.getText().toString().trim(), "ANDROID");							
			} else {
				postHttpRequest.actionNew(postField.getText().toString().trim(),"", "ANDROID");
			}
		}
		manager.request(this, postHttpRequest, POST_WRITE_REQUEST);
		User me = session.getCurrentUser();
		me.setPostCount(me.getPostCount() + 1);
	}

	
	public void onTagButtonClicked(View v){
		
		if (tagsField.getVisibility() == View.GONE){
			tagsField.setVisibility(View.VISIBLE);
			tagsField.requestFocus();
			showKeyboard(tagsField);
		}
		else {
			tagsField.setVisibility(View.GONE);
			postField.requestFocus();
			showKeyboard(postField);
		}
	}
	
	
	public void onTwitterButtonClicked(View v){
		
	}
	
	public void onFacebookButtonClicked(View w){
		
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

	
	public void onSoftKeyboardShown(boolean isShowing) {
		// TODO Auto-generated method stub
		if (isShowing){
	    	contentHeightWithoutKeyboard = contentWrapper.getHeight();
		}else {
			contentHeightWithKeyboard = contentWrapper.getHeight();
		}
		
		if (keyboardHeight <= 0 && contentHeightWithoutKeyboard > 0 && contentHeightWithKeyboard > 0)
			keyboardHeight = contentHeightWithoutKeyboard - contentHeightWithKeyboard;
			
		if (!isShowing){
			postFooterContentLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, keyboardHeight));
			postFooterContentLayout.setVisibility(View.VISIBLE);
		}else {
			postFooterContentLayout.setVisibility(View.GONE);
		}
	}	
}
