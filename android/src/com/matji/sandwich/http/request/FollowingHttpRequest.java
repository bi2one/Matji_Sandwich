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
	private String action;
	private boolean isPost;

	public FollowingHttpRequest() {
		postHashtable = new Hashtable<String, Object>();
		getHashtable = new Hashtable<String, String>();
		initParam();
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
	
	public void actionList() {
		isPost = false;
		action = "list";
		
		getHashtable.clear();
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
		MatjiDataParser parser = null;

		if (action.equals("new") || action.equals("list") || action.equals("following_list") || action.equals("follower_list")) {
			parser = new FollowingParser(action);
		}
		else if (action.equals("delete")) {
			parser = new FollowingParser(action);
		}
		else { 
			// TODO action ERROR
		}
		
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