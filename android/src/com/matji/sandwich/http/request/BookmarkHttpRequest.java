package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.BookmarkParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class BookmarkHttpRequest extends HttpRequest {
    private MatjiDataParser parser;
    private String action;
    private String controller;
    
    public BookmarkHttpRequest(Context context) {
    	super(context);
    	controller = "stores";
    }
    
    public void actionBookmark(int store_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "bookmark";
    	parser = new BookmarkParser();
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionUnBookmark(int store_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "unbookmark";
    	parser = new BookmarkParser();
    	
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

    	Log.d("Matji", "StoreHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "StoreHttpRequest resultCode: " + resultCode);

    	return parser.parseToMatjiDataList(resultBody);
    }
}