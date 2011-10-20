package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.StoreParser;

import android.content.Context;

public class StoreCloseHttpRequest extends HttpRequest {

	public StoreCloseHttpRequest(Context context) {
		super(context);
		controller = "stores";
	}
	
	public void actionClose(int store_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "closed";
		parser = new StoreParser();
		
		postHashtable.clear();
		postHashtable.put("store_id", store_id);
	}
	
}
