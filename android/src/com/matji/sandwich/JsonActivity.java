package com.matji.sandwich;


import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.parser.UserParser;
import com.matji.sandwich.exception.MatjiException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class JsonActivity extends Activity{
	String string ="{\"result\":[{\"intro\":null,\"title\":null,\"following_count\":1,\"nick\":\"test3\",\"id\":100048492,\"tag_count\":0,\"follower_count\":0,\"userid\":\"test3\",\"followed\":false,\"post_count\":1,\"store_count\":1,\"following\":false}],\"code\":200}"; 
	ArrayList<MatjiData> datas;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		UserParser parser = new UserParser();
		try {
			datas = parser.getData(string);
		} catch (MatjiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Log.d("asdfasdf",((Store)datas.get(0)).getAddress()); //store
//		Log.d("saf",((Post)datas.get(0)).getPost()); //post
//		Log.d("saf",((User)datas.get(0)).getUserid()); //user
		
	}
		
}
