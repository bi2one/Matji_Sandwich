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
//		Hashtable<String, String> table = new Hashtable<String, String>();
		//table.put("page", "1");
//		table.put("food_id", "1");
		FoodHttpRequest request = new FoodHttpRequest();
		request.actionNew(12296, "육개장!!!");

		manager.request(request, 1);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		Food foodData = (Food)data.get(0);
		Log.d("RESULT!!", ""+foodData.getId());
		Log.d("RESULT!!", "tag: " + tag);
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}
}


//		Log.d(\"saf\",((AttachFile)datas.get(0)).getFilename()); //attach_file
//		Log.d(\"saf\",\"\"+((Alarm)datas.get(0)).getSent_user_id()); //alarm
//		Log.d(\"saf\",\"\"+((Comment)datas.get(0)).getId()); //comment => post_id
//		Log.d(\"saf\",\"\"+((Following)datas.get(0)).getFollowing_user_id()); //following
//		Log.d("saf",""+((Food)datas.get(0)).getName()); //food
//		Log.d(\"saf\",\"\"+((Message)datas.get(0)).getId()); //message
//		Log.d(\"saf\",\"\"+((Notice)datas.get(0)).getId()); //notice 
//		Log.d(\"saf\",((Post)datas.get(0)).getPost()); //post
//		Log.d(\"asdfasdf\",((Store)datas.get(0)).getAddress()); //store
//		Log.d(\"saf\",\"\"+((Tag)datas.get(0)).getId()); //tag
//		Log.d(\"saf\",User)datas.get(0)).getUserid()); //user