package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.FoodParser;
import com.matji.sandwich.http.parser.LikeParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.StringMessageParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class FoodHttpRequest extends HttpRequest {
	private Hashtable<String, Object> postHashtable;
	private Hashtable<String, String> getHashtable;
	private MatjiDataParser parser;
	private String action;
	private boolean isPost;

	public FoodHttpRequest() {		
		postHashtable = new Hashtable<String, Object>();
		getHashtable = new Hashtable<String, String>();
	}

	public void actionNew(int store_id, String food_name) {
		isPost = true;
		action = "new";
		parser = new FoodParser(); 
		
		postHashtable.clear();
		postHashtable.put("store_id", store_id);
		postHashtable.put("food_name", food_name);
		postHashtable.put("access_token", access_token);
	}

	public void actionDelete(int store_food_id) {
		isPost = true;
		action = "delete";
		parser = new StringMessageParser();
		
		postHashtable.clear();
		postHashtable.put("store_food_id", store_food_id);
		postHashtable.put("access_token", access_token);
	}
	
	public void actionUnlike(int store_food_id) {
		isPost = true;
		action = "unlike";
		parser = new StringMessageParser();
		
		postHashtable.clear();
		postHashtable.put("store_food_id", store_food_id);
		postHashtable.put("access_token", access_token);
	}
	
	public void actionLike(int store_food_id) {
		isPost = true;
		action = "like";
		parser = new LikeParser();
		
		postHashtable.clear();
		postHashtable.put("store_food_id", store_food_id);
		postHashtable.put("access_token", access_token);
	}
	
	public void actionList(int store_id) {
		isPost = false;
		action = "list";
		parser = new FoodParser(); 
		
		getHashtable.clear();
		getHashtable.put("store_id", store_id + "");
	}

	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(isPost) ? 
					requestHttpResponsePost(serverDomain + "foods/" + action + ".json?", null, postHashtable)
					:requestHttpResponseGet(serverDomain + "foods/" + action + ".json?", null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "FoodHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "FoodHttpRequest resultCode: " + resultCode);
	
		return parser.parseToMatjiDataList(resultBody);
	}
}