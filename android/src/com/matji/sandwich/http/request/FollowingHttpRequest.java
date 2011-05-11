package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.FollowingParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
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
	private String access_token;
	private boolean isPost;
	private int followed_user_id;
	private int user_id;

	public FollowingHttpRequest() {
		parser = new FollowingParser();
		postHashtable = new Hashtable<String, Object>();
		getHashtable = new Hashtable<String, String>();
		initParam();
	}
	
	public void actionNew(int followed_user_id) {
		this.isPost = true;
		this.action = "new";
		this.followed_user_id = followed_user_id;
		
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
		
	}
	
	public void actionDelete(int followed_user_id) {
		this.isPost = true;
		this.action = "delete";
		this.followed_user_id = followed_user_id;
		
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
	}
	
	public void actionList() {
		this.isPost = false;
		this.action = "list";
		
		getHashtable.clear();
	}
	
	public void actionFollowingList(int user_id) {
		this.isPost = false;
		this.action = "following_list";
		this.user_id = user_id;
	
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");

	}
	
	public void actionFollowerList(int user_id) {
		this.isPost = false;
		this.action = "follower_list";
		this.user_id = user_id;
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
	}

	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(isPost) ?
					requestHttpResponsePost(serverDomain + "followings/" + action + ".json?", null, postHashtable)
					:requestHttpResponseGet(serverDomain + "followings/" + action + ".json?", null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Check", "FollowingHttpRequest resultBody: " + resultBody);
		Log.d("Check", "FollowingHttpRequest resultCode: " + resultCode);

		return parser.getData(resultBody);
	}

	public void initParam() {}
}