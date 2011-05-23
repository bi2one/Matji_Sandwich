package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.UserHttpRequest;

import android.app.Activity;
import android.os.Bundle;

public class MultipleRequestTest extends Activity implements Requestable {
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.store_main);

	HttpRequestManager manager = new HttpRequestManager(getApplicationContext(), this);
	UserHttpRequest request = new UserHttpRequest(getApplicationContext());
	request.actionList();
	manager.request(this, request, 0);
	
	UserHttpRequest request2 = new UserHttpRequest(getApplicationContext());
	request2.actionShow(100000001);
	manager.request(this, request2, 1);
    }

    public void requestCallBack(int tag, ArrayList<? extends MatjiData> data) { }
    public void requestExceptionCallBack(int tag, MatjiException e) { }
}
