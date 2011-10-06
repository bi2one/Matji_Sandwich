package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.BookmarkParser;

import android.content.Context;

public class BookmarkHttpRequest extends HttpRequest {
    public BookmarkHttpRequest(Context context) {
    	super(context);
    	controller = "stores";
    }
    
    public void actionBookmark(int store_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "bookmark";
    	parser = new BookmarkParser(getContext());
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionUnBookmark(int store_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "unbookmark";
    	parser = new BookmarkParser(getContext());
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }
}