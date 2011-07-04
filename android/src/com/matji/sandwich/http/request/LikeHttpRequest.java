package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.LikeParser;

import android.content.Context;

public class LikeHttpRequest extends HttpRequest {
	public LikeHttpRequest(Context context) {	
		super(context);
		controller = "foods";
	}

	public void actionFoodUnlike(int store_food_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "unlike";
		parser = new LikeParser(context);
		controller = "foods";
		
		postHashtable.clear();
		postHashtable.put("store_food_id", store_food_id);
	}
	
	public void actionFoodLike(int store_food_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "like";
		parser = new LikeParser(context);
		controller = "foods";
			
		postHashtable.clear();
		postHashtable.put("store_food_id", store_food_id);
	}

    public void actionStoreUnLike(int store_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "unlike";
    	parser = new LikeParser(context);
    	controller = "stores";
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionStoreLike(int store_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "like";
    	parser = new LikeParser(context);
    	controller = "stores";

    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }    
    
    public void actionPostUnLike(int post_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "unlike";
    	parser = new LikeParser(context);
    	controller = "posts";

    	postHashtable.clear();
    	postHashtable.put("post_id", post_id);
    }
    
    public void actionPostLike(int post_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "like";
    	parser = new LikeParser(context);
    	controller = "posts";

    	postHashtable.clear();
    	postHashtable.put("post_id", post_id);
    }
}