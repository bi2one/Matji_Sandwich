package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.StoreFoodParser;

import android.content.Context;

public class StoreFoodHttpRequest extends HttpRequest {
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
	
	public void actionList(int store_id, int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";
		parser = new StoreFoodParser(); 
		
		getHashtable.clear();
		getHashtable.put("store_id", store_id + "");
		getHashtable.put("page", page + "");
		getHashtable.put("limit", limit + "");
	}

    public void actionAccuracyUp(int store_food_id) {
        httpMethod = HttpMethod.HTTP_GET;
        action = "accuracy_up";
        parser = new StoreFoodParser();
        
        getHashtable.clear();
        getHashtable.put("store_food_id", store_food_id+"");
    }
    
    public void actionAccuracyDown(int store_food_id) {
        httpMethod = HttpMethod.HTTP_GET;
        action = "accuracy_down";
        parser = new StoreFoodParser();
        
        getHashtable.clear();
        getHashtable.put("store_food_id", store_food_id+"");
    }
}