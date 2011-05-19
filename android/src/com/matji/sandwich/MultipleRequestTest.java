package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.UserHttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MultipleRequestTest extends Activity implements Requestable {
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.store_info);

	HttpRequestManager manager = new HttpRequestManager(getApplicationContext(), this);
	UserHttpRequest request = new UserHttpRequest();
	request.actionList();
	manager.request(this, request, 0);
	
	UserHttpRequest request2 = new UserHttpRequest();
	request2.actionShow(100000001);
	manager.request(this, request2, 1);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) { }
    public void requestExceptionCallBack(int tag, MatjiException e) { }
}
