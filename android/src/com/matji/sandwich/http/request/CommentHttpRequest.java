package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.CommentParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class CommentHttpRequest extends HttpRequest {
	private MatjiDataParser parser;
	private String controller;
	private String action;
	
	public CommentHttpRequest(Context context) {
		super(context);
		parser = new CommentParser();
		controller = "comments";
	}
	
	public void actionNew(int post_id, String comment, String from_where) {

		parser = new CommentParser();
		httpMethod = HttpMethod.HTTP_POST;
		action = "new";
				
		postHashtable.clear();
		postHashtable.put("post_id", post_id);
		postHashtable.put("comment", comment);
		postHashtable.put("from_where", from_where);
	}
	
	public void actionDelete(int comment_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "delete";
		
		postHashtable.clear();
		postHashtable.put("comment_id", comment_id);
	}
	
	public void actionList(int post_id, int page, int limit) {
		parser = new CommentParser();
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";

		getHashtable.clear();
		getHashtable.put("post_id", post_id+ "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");  	
		getHashtable.put("include", "user");
	}
	

	public void actionShow(int comment_id) {
		parser = new CommentParser();
		httpMethod = HttpMethod.HTTP_GET;
		action = "show";
		
		getHashtable.clear();
		getHashtable.put("comment_id", "" + comment_id);
	}

	public ArrayList<MatjiData> request() throws MatjiException {		

		SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ? 
					requestHttpResponsePost(serverDomain + controller +"/" + action, null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller +"/" + action, null, getHashtable); 

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "CommentresultBody: " + resultBody);
		Log.d("Matji", "CommentresultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}