package com.matji.sandwich.http.request;

import android.content.Context;

import com.matji.sandwich.http.parser.StoreDetailInfoParser;
import com.matji.sandwich.http.parser.StoreParser;

public class StoreDetailHttpRequest extends HttpRequest {
	public StoreDetailHttpRequest(Context context) {
		super(context);
		controller = "store_details";
	}
	
	public void actionList(int store_id, int page, int limit){
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";
		parser = new StoreDetailInfoParser();

		getHashtable.clear();
		getHashtable.put("store_id", store_id + "");
		getHashtable.put("page", page + "");
		getHashtable.put("limit", limit + "");
		getHashtable.put("include", "user");
	}

	public void actionNew(int store_id, String note){
		httpMethod = HttpMethod.HTTP_POST;
		action = "new";
		parser = new StoreParser();

		postHashtable.clear();
		postHashtable.put("store_id", store_id);
		postHashtable.put("note", note);
	}
}
