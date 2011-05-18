package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.CommentParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class CommentHttpRequest extends HttpRequest {
	private Hashtable<String, Object> postHashtable;
	private Hashtable<String, String> getHashtable;
	private MatjiDataParser parser;
	private String controller;
	private String action;
	private boolean isPost;
	
	public CommentHttpRequest() {
		postHashtable = new Hashtable<String, Object>();
		getHashtable = new Hashtable<String, String>();
		parser = new CommentParser();
		controller = "comments";
	}
	
	public void actionNew(int post_id, String comment, String from_where) {
		isPost = true;
		action = "new";
		
		postHashtable.clear();
		postHashtable.put("post_id", post_id);
		postHashtable.put("comment", comment);
		postHashtable.put("from_where", from_where);
	}
	
	public void actionDelete(int comment_id) {
		isPost = true;
		action = "delete";
		
		postHashtable.clear();
		postHashtable.put("comment_id", comment_id);
	}
	
	public void actionList(int post_id) {
		isPost = false;
		action = "list";

		getHashtable.clear();
		getHashtable.put("post_id", post_id+ "");
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