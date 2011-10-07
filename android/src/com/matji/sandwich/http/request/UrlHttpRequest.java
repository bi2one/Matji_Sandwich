package com.matji.sandwich.http.request;

import android.content.Context;

import com.matji.sandwich.http.parser.UrlParser;

public class UrlHttpRequest extends HttpRequest {
    
    public enum UrlType {
        STORE, USER,
    }
    
	public UrlHttpRequest(Context context) {
		super(context);
		controller = "urls";
	}

	public void actionUrlList(UrlType type, int id, int page, int limit) {
	    switch (type) {
	    case STORE:
	        actionStoreUrlList(id, page, limit);
	        break;
	    case USER:
	        actionUserUrlList(id, page, limit);
	        break;
	    }
	}
	
	public void actionStoreUrlList(int store_id, int page, int limit) {
		action = "store_url_list";
		parser = new UrlParser();
		
		getHashtable.clear();
		getHashtable.put("store_id", store_id + "");
		getHashtable.put("page", page + "");
		getHashtable.put("limit", limit + "");
	}
	
	public void actionUserUrlList(int user_id, int page, int limit) {
		action = "user_url_list";
		parser = new UrlParser();
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
		getHashtable.put("page", page + "");
		getHashtable.put("limit", limit + "");
	}
}