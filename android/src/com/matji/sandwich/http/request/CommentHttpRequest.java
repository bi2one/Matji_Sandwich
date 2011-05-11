package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.CommentParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class CommentHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
	private MatjiDataParser parser;
	private String action;
	private String access_token;
	private boolean isPost;
	
	public CommentHttpRequest() {
		access_token = "7f07cb18e1ccfc1d5493f08f32ac51a7d64b222d"; //임시
		postHashtable = new Hashtable<String, Object>();
		getHashtable = new Hashtable<String, String>();
	}
	
	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(isPost) ? 
					requestHttpResponsePost(serverDomain + "comments/" + action + ".json?", null, postHashtable)
					:requestHttpResponseGet(serverDomain + "comments/" + action + ".json?", null, getHashtable); 

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Check", "CommentHttpRequest resultBody: " + resultBody);
		Log.d("Check", "CommentHttpRequest resultCode: " + resultCode);

		return parser.getData(resultBody);
	}
}