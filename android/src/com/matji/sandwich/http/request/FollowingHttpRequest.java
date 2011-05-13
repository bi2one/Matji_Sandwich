package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.FollowingParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.StringMessageParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class FollowingHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
	private MatjiDataParser parser;
	private String action;
	private boolean isPost;

	public FollowingHttpRequest() {
		getHashtable = new Hashtable<String, String>();
		postHashtable = new Hashtable<String, Object>();
	}
	
	public void actionNew(int followed_user_id) {
		isPost = true;
		action = "new";
		parser = new FollowingParser();
		
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
	}
	
	public void actionDelete(int followed_user_id) {
		isPost = true;
		action = "delete";
		parser = new StringMessageParser();
				
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
	}
	
	public void actionList() {
		isPost = false;
		action = "list";
		parser = new FollowingParser();
		
		getHashtable.clear();
	}
	
	public void actionFollowingList(int user_id) {
		isPost = false;
		action = "following_list";
		parser = new FollowingParser();
	
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
	}
	
	public void actionFollowerList(int user_id) {
		isPost = false;
		action = "follower_list";
		parser = new FollowingParser();
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
		postHashtable.put("access_token", access_token);
	}

	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(isPost) ?
					requestHttpResponsePost(serverDomain + "followings/" + action + ".json?", null, postHashtable)
					:requestHttpResponseGet(serverDomain + "followings/" + action + ".json?", null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "FollowingHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "FollowingHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}