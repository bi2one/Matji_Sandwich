package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.CurrentUserParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class CurrentUserHttpRequest extends HttpRequest {
	private Hashtable<String, Object> postHashtable;
	private Hashtable<String, String> getHashtable;
	private MatjiDataParser parser;
	private String controller;
	private String action;
	private boolean isPost;
	
	public CurrentUserHttpRequest() {
		postHashtable = new Hashtable<String, Object>();
		getHashtable = new Hashtable<String, String>();
		controller = "comments";
	}
		
	public ArrayList<MatjiData> request() throws MatjiException {		
		SimpleHttpResponse response = 
			(isPost) ? 
					requestHttpResponsePost(serverDomain + controller +"/" + action, null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller +"/" + action, null, getHashtable); 

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "CommentresultBody: " + resultBody);
		Log.d("Matji", "CommentresultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}