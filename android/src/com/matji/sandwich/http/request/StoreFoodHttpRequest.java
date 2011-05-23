package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.StoreFoodParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class StoreFoodHttpRequest extends HttpRequest {
	private MatjiDataParser<StoreFood> parser;
	private String action;
	private String controller;
	
	public StoreFoodHttpRequest(Context context) {	
		super(context);
		controller = "foods";
	}

	public void actionNew(int store_id, String food_name) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "new";
		parser = new StoreFoodParser();
		
		postHashtable.clear();
		postHashtable.put("store_id", store_id);
		postHashtable.put("food_name", food_name);
	}

	public void actionDelete(int store_food_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "delete";
		parser = new StoreFoodParser();
		
		postHashtable.clear();
		postHashtable.put("store_food_id", store_food_id);
	}
	
	public void actionList(int store_id) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";
		parser = new StoreFoodParser(); 
		
		getHashtable.clear();
		getHashtable.put("store_id", store_id + "");
	}

	public ArrayList<StoreFood> request() throws MatjiException {
		SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ? 
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "FoodHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "FoodHttpRequest resultCode: " + resultCode);
	
		return parser.parseToMatjiDataList(resultBody);
	}
}