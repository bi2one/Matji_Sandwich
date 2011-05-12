//이 곳은 JSON 테스트를 하는곳입니다.

package com.matji.sandwich;

import com.matji.sandwich.data.*;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.*;
import com.matji.sandwich.exception.MatjiException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.Hashtable;
import java.util.ArrayList;

public class JsonActivity extends Activity implements Requestable{
	HttpRequestManager manager;
    private String access_token="7f07cb18e1ccfc1d5493f08f32ac51a7d64b222d";

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		manager = new HttpRequestManager(getApplicationContext(), this);
		request();
	}

	private void request() {
		PostHttpRequest request = new PostHttpRequest();
//		request.actionDelete(32);
//		request.actionNew(12296, "TEST1");
		request.actionDelete(16931, access_token);
//		request.actionLike(32);		
		manager.request(request, 1);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		Post foodData = (Post)data.get(0);
		Log.d("Matji", ""+foodData.getId());
		Log.d("Matji", "tag: " + tag);
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}
}
