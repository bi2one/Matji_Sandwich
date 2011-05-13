package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.BookmarkParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class BookmarkHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
	private MatjiDataParser parser;
	private String action;
	private boolean isPost;
	private String controller;
	
    public BookmarkHttpRequest() {
    	getHashtable = new Hashtable<String, String>();
    	postHashtable = new Hashtable<String, Object>();
    	controller = "bookmarks";
    }
    
    public ArrayList<MatjiData> request() throws MatjiException {
    	SimpleHttpResponse response = 
			(isPost) ? 
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";
	
		Log.d("Matji", "BookmarkHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "BookmarkHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
    }
}