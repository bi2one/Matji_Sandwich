package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.FollowingParser;
import com.matji.sandwich.http.parser.UserParser;

import android.content.Context;

public class FollowingHttpRequest extends HttpRequest {
	public FollowingHttpRequest(Context context) {
		super(context);
		controller = "followings";
	}
	
	public void actionNew(int followed_user_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "new";
		parser = new FollowingParser(context);
		
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
	}
	
	public void actionList(int user_id, int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";
		parser = new UserParser(context);
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id+"");
		getHashtable.put("order", "nick");
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
	}
	
	public void actionDelete(int followed_user_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "delete";
		parser = new FollowingParser(context);
		
				
		postHashtable.clear();
		postHashtable.put("followed_user_id", followed_user_id);
	}
	
	public void actionFollowingList(int user_id, int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "following_list";
		parser = new UserParser(context);
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
		getHashtable.put("include", "post");
	}

    public void actionFollowingRankingList(int user_id, int page, int limit) {
	httpMethod = HttpMethod.HTTP_GET;
	action = "following_list";
	parser = new UserParser(context);

	getHashtable.clear();
	getHashtable.put("user_id", user_id + "");
	getHashtable.put("page", page + "");
	getHashtable.put("limit", limit + "");
	getHashtable.put("join", "user_mileage");
	getHashtable.put("order", "user_mileages.total_point desc");
    }
	
	public void actionFollowerList(int user_id, int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "follower_list";
		parser = new UserParser(context);
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
	}	
	
	public void actionFollowerList(int user_id, int page, int limit, boolean requestPost) {
		actionFollowerList(user_id, page, limit);
		
		getHashtable.put("include", "post");
	}
}