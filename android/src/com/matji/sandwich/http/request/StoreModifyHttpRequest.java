package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.StoreParser;

import android.content.Context;

public class StoreModifyHttpRequest extends HttpRequest {

	public StoreModifyHttpRequest(Context context) {
		super(context);
		controller = "stores";
	}
	
	public void actionModify(String name, String address, double lat, double lng, int store_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "modify";
		parser = new StoreParser(getContext());
		
		getHashtable.clear();
		getHashtable.put("name", name);
		getHashtable.put("address", name);
		getHashtable.put("lat", lat + "");
		getHashtable.put("lng", lng + "");
		getHashtable.put("store_id", store_id + "");
		
	}
	
}
