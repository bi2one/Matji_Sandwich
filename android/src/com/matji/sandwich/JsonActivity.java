//이 곳은 JSON 테스트를 하는곳입니다.

package com.matji.sandwich;

import com.matji.sandwich.data.*;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.FoodHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.exception.MatjiException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.Hashtable;
import java.util.ArrayList;

public class JsonActivity extends Activity implements Requestable{
	HttpRequestManager manager;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		manager = new HttpRequestManager(getApplicationContext(), this);
		request();
	}

	private void request() {
		FoodHttpRequest request = new FoodHttpRequest();
//		request.actionDelete(32);
//		request.actionNew(12296, "TEST1");
		request.actionList(12296);
//		request.actionLike(32);		
		manager.request(request, 1);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
//		Alarm foodData = (Alarm)data.get(0);
//		Food foodData = (Food)data.get(0);
//		Log.d("Matji", ""+foodData.getId());
//		Log.d("Matji", "tag: " + tag);
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}
}
