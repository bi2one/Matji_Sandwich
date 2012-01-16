package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.StoreParser;

import android.content.Context;

public class StoreModifyHttpRequest extends HttpRequest {

	public StoreModifyHttpRequest(Context context) {
		super(context);
		controller = "stores";
	}
	
	public void actionModify(String name, String address, String add_address, double lat, double lng, int store_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "modify";
		parser = new StoreParser();
		
		postHashtable.clear();
		postHashtable.put("name", name);
		postHashtable.put("address", address);
		postHashtable.put("add_address", add_address);
		postHashtable.put("lat", lat + "");
		postHashtable.put("lng", lng + "");
		postHashtable.put("store_id", store_id + "");
		
	}
	
}
