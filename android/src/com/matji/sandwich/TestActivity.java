package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.Hashtable;
import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Simple;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.SimpleHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.exception.MatjiException;

public class TestActivity extends Activity implements Requestable {
    HttpRequestManager manager;
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	manager = new HttpRequestManager(getApplicationContext(), this);
	request();
    }

    private void request() {
	Hashtable<String, String> table = new Hashtable<String, String>();
	table.put("page", "1");
	table.put("member_id", "100000309");
	table.put("order", "Store.count desc");
	HttpRequest request = new SimpleHttpRequest();
	request.setStringHashtable(table);
	manager.request(request, 1);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	Simple simpleData = (Simple)data.get(0);
	Log.d("RESULT!!", simpleData.getContent());
	Log.d("RESULT!!", "tag: " + tag);
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.showToastMsg(getApplicationContext());
    }
}