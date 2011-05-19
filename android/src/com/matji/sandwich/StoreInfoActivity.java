package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.FoodHttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;

import android.app.Activity;
import android.os.Bundle;

public class StoreInfoActivity extends Activity implements Requestable {	
	HttpRequestManager manager;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_info);		
		manager = new HttpRequestManager(getApplicationContext(), this);
		request();
	}

	private void request() {
		StoreHttpRequest request = new StoreHttpRequest();
		request.actionShow(12296);
		manager.request(request, 1);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}
}