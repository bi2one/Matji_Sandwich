package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.FollowingParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class FollowingHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
	private MatjiDataParser parser;
	private String controller;
	private String action;
	private boolean isPost;

	public FollowingHttpRequest() {
		getHashtable = new Hashtable<String, String>();
		postHashtable = new Hashtable<String, Object>();
		parser = new FollowingParser();
		controller = "followings";
	}
	
	public void actionNew(int followed_user_id) {
		isPost = true;
		action = "new";
		
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
	}
	
	public void actionDelete(int followed_user_id) {
		isPost = true;
		action = "delete";
				
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
	}
	
	public void actionFollowingList(int user_id) {
		isPost = false;
		action = "following_list";
	
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
	}
	
	public void actionFollowerList(int user_id) {
		isPost = false;
		action = "follower_list";
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
	}

	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(isPost) ?
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "FollowingHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "FollowingHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}