package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.FollowingParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.Following;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class FollowingHttpRequest extends HttpRequest {
	private MatjiDataParser<Following> parser;
	private String controller;
	private String action;


	public FollowingHttpRequest(Context context) {
		super(context);
		parser = new FollowingParser();
		controller = "followings";
	}
	
	public void actionNew(int followed_user_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "new";
		
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
	}
	
	public void actionDelete(int followed_user_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "delete";
				
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
	}
	
	public void actionFollowingList(int user_id) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "following_list";
	
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
	}
	
	public void actionFollowerList(int user_id) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "follower_list";
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
	}

	public ArrayList<Following> request() throws MatjiException {
		SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ?
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "FollowingHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "FollowingHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}