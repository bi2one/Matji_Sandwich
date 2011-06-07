package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.PostParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class PostHttpRequest extends HttpRequest {
    private MatjiDataParser parser;
    private String action;
    private String controller;
    
    public PostHttpRequest(Context context) {
    	super(context);
		parser = new PostParser();
		controller = "posts";
    }

    public void actionShow(int post_id){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "show";
    	parser = new PostParser();
    	
    	getHashtable.clear();
    	getHashtable.put("post_id", post_id + "");
    }
    
    public void actionNew(String post, String tags) {
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	parser = new PostParser();
    	
    	postHashtable.clear();
    	postHashtable.put("post", post);
    	postHashtable.put("tags", tags);
    }
    
    public void actionDelete(int post_id, String access_token){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "delete";
    	
    	postHashtable.clear();
    	postHashtable.put("post_id",post_id + "");
    }
    
    public void actionUnlike(int post_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "unlike";
    	
    	postHashtable.clear();
    	postHashtable.put("post_id", post_id + "");
    }
    
    public void actionLike(int post_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "like";
    	parser = new PostParser();
    	
    	postHashtable.clear();
    	postHashtable.put("post_id", post_id + "");
    }
    
    public void actionList(int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;	
    	action = "list";
    	parser = new PostParser();
    	
    	getHashtable.clear();
    	getHashtable.put("page", "" + page);
    	getHashtable.put("limit", "" + limit);    	
		getHashtable.put("include", "user,store");
    }
    
    public void actionStoreList(int store_id, int page, int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "store_list";
    	parser = new PostParser();
    	
    	getHashtable.clear();
    	getHashtable.put("store_id",store_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");  	
		getHashtable.put("include", "user,store");
    }
    
    public void actionUserList(int user_id, int page, int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "user_list";
    	parser = new PostParser();
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");  	
		getHashtable.put("include", "user,store");
    }
    
    public void actionMyList(int page , int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "my_list";
    	parser = new PostParser();
    	
    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    
    public void actionNearbyList(int lat_ne, int lat_sw, int lng_sw, int lng_ne, int page, int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "nearby_list";
    	
    	getHashtable.clear();
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionSearch(String keyword, int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "search";
    	
    	getHashtable.clear();
    	getHashtable.put("q", keyword);
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public ArrayList<MatjiData> request() throws MatjiException {
    	SimpleHttpResponse response = 
    		(httpMethod == HttpMethod.HTTP_POST) ? 
    				requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
    				:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable); 
	
    	String resultBody = response.getHttpResponseBodyAsString();
    	String resultCode = response.getHttpStatusCode() + "";

    	Log.d("Matji", "PostHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "PostHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
    }
}