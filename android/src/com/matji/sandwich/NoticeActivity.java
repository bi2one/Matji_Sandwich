package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;

import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.*;


import android.os.Bundle;

public class NoticeActivity extends BaseActivity implements Requestable {
	private static final int NOTICE_REQUEST = 1 ;
	HttpRequestManager manager;
	private NoticeHttpRequest noticeHttpRequest;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		manager = new HttpRequestManager(getApplicationContext(), this);
		request();
	}
	
	private void request() {
		noticeHttpRequest = new NoticeHttpRequest(getApplicationContext());
		noticeHttpRequest.actionList();
		manager.request(this, noticeHttpRequest, NOTICE_REQUEST);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
//		Notice notice = (Notice)data.get(0);
		
	}


	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}
}
