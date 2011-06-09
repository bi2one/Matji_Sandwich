package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.LikeParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class LikeHttpRequest extends HttpRequest {
	private MatjiDataParser parser;
	private String action;
	private String controller;
	
	public LikeHttpRequest(Context context) {	
		super(context);
		controller = "foods";
	}

	public void actionFoodUnlike(int store_food_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "unlike";
		parser = new LikeParser(context);
		controller = "foods";
		
		postHashtable.clear();
		postHashtable.put("store_food_id", store_food_id);
	}
	
	public void actionFoodLike(int store_food_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "like";
		parser = new LikeParser(context);
		controller = "foods";
			
		postHashtable.clear();
		postHashtable.put("store_food_id", store_food_id);
	}

    public void actionStoreUnLike(int store_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "unlike";
    	parser = new LikeParser(context);
    	controller = "stores";
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionStoreLike(int store_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "like";
    	parser = new LikeParser(context);
    	controller = "stores";

    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }

	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ? 
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "LikeHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "LikeHttpRequest resultCode: " + resultCode);
	
		return parser.parseToMatjiDataList(resultBody);
	}
}