package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.TagParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class TagHttpRequest extends HttpRequest {
//	private Hashtable<String, Object> postHashtable;
	private Hashtable<String, String> getHashtable;
	private MatjiDataParser parser;
	private String action;
	private String controller;
	//	private boolean isPost; // tag 는 전부  GET 방식..

	public TagHttpRequest() {
//		postHashtable = new Hashtable<String, Object>();
		getHashtable = new Hashtable<String, String>();
		controller = "tags";
	}

	public void actionShow(int tag_id) {
		action = "show";
		
		getHashtable.clear();
		getHashtable.put("tag_id", tag_id + "");
		parser = new TagParser();
	}
	
	public void actionList() {
		action = "list";
		
		getHashtable.clear();
		parser = new TagParser();
	}
	
	public void actionStoreTagList(int store_id) {
		action = "store_tag_list";
		
		getHashtable.clear();
		getHashtable.put("store_id", store_id + "");
		parser = new TagParser();
	}
	
	public void actionUserTagList(int user_id) {
		action = "user_tag_list";
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id+ "");
		parser = new TagParser();
	}
	
	public void actionPostTagList(int post_id) {
		action = "post_tag_list";
		
		getHashtable.clear();
		getHashtable.put("post_id", post_id + "");
		parser = new TagParser();
	}
	
	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable); 

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "TagHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "TagHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}