package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.UrlParser;

import android.content.Context;

public class UrlHttpRequest extends HttpRequest {
	public UrlHttpRequest(Context context) {
		super(context);
		controller = "urls";
	}

	public void actionStoreUrlList(int store_id, int page, int limit) {
		action = "store_url_list";
		parser = new UrlParser(context);
		
		getHashtable.clear();
		getHashtable.put("store_id", store_id + "");
		getHashtable.put("page", page + "");
		getHashtable.put("limit", limit + "");
	}
	
	public void actionUserUrlList(int user_id, int page, int limit) {
		action = "user_url_list";
		parser = new UrlParser(context);
		
		getHashtable.clear();
		getHashtable.put("store_id", user_id + "");
		getHashtable.put("page", page + "");
		getHashtable.put("limit", limit + "");
	}
}