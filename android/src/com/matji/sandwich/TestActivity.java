package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.Hashtable;
import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Simple;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.exception.MatjiException;

public class TestActivity<T> extends Activity implements Requestable<T> {
    HttpRequestManager manager;
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Log.d("RESULT!!", "11111");
	setContentView(R.layout.main);
	Log.d("RESULT!!", "22222");
	manager = new HttpRequestManager(getApplicationContext(), this);
	Log.d("RESULT!!", "33333");
	request();
	Log.d("RESULT!!", "444444");
    }

    private void request() {
	Hashtable<String, String> table = new Hashtable<String, String>();
	table.put("page", "1");
	table.put("member_id", "100000309");
	table.put("order", "Store.count desc");
//	HttpRequest request = new SimpleHttpRequest();
	//request.setStringHashtable(table);
//	manager.request(request, 1);
    }

    public void requestCallBack(int tag, ArrayList<T> data) {
	Simple simpleData = (Simple)data.get(0);
	Log.d("RESULT!!", simpleData.getContent());
	Log.d("RESULT!!", "tag: " + tag);
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.showToastMsg(getApplicationContext());
    }
}