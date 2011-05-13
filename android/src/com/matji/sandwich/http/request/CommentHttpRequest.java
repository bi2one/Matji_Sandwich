package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.CommentParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.StringMessageParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class CommentHttpRequest extends HttpRequest {
	private Hashtable<String, Object> postHashtable;
	private Hashtable<String, String> getHashtable;
	private MatjiDataParser parser;
	private String action;
	private boolean isPost;
	
	public CommentHttpRequest() {
		postHashtable = new Hashtable<String, Object>();
		getHashtable = new Hashtable<String, String>();
	}
	
	public void actionNew(int post_id, String comment, String from_where) {
		isPost = true;
		action = "new";
		parser = new CommentParser();
		
		postHashtable.clear();
		postHashtable.put("post_id", post_id);
		postHashtable.put("comment", comment);
		postHashtable.put("from_where", from_where);
		postHashtable.put("access_token", access_token);
	}
	
	public void actionDelete(int post_id) {
		isPost = true;
		action = "delete";
		parser = new StringMessageParser();
		
		postHashtable.clear();
		postHashtable.put("post_id", post_id);
	}
	
	public void actionList(int post_id) {
		isPost = false;
		action = "list";
		parser = new CommentParser();

		getHashtable.clear();
		getHashtable.put("post_id", post_id+ "");
	}
	
	public ArrayList<MatjiData> request() throws MatjiException {		
		SimpleHttpResponse response = 
			(isPost) ? 
					requestHttpResponsePost(serverDomain + "comments/" + action + ".json?", null, postHashtable)
					:requestHttpResponseGet(serverDomain + "comments/" + action + ".json?", null, getHashtable); 

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "CommentHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "CommentHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}