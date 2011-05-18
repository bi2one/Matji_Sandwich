package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.*;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class PostHttpRequest extends HttpRequest {
    private Hashtable<String, String> getHashtable;
    private Hashtable<String, Object> postHashtable;
    private MatjiDataParser parser;
    private boolean isPost;
    private String action;
    private String controller;
    
    public PostHttpRequest() {
		getHashtable = new Hashtable<String, String>();
		postHashtable = new Hashtable<String, Object>();
		parser = new PostParser();
		controller = "posts";
    }

    public void actionShow(int post_id){
    	isPost = false;
    	action = "show";
    	
    	getHashtable.clear();
    	getHashtable.put("post_id", post_id + "");
    }
    
    public void actionNew(String post, String access_token){
    	isPost = true;
    	action = "new";
    	
    	postHashtable.clear();
    	postHashtable.put("post", post);
    }
    
    public void actionDelete(int post_id, String access_token){
    	isPost = true;
    	action = "delete";
    	
    	postHashtable.clear();
    	postHashtable.put("post_id",post_id + "");
    }
    
    public void actionUnlike(int post_id){
    	isPost = true;
    	action = "unlike";
    	
    	postHashtable.clear();
    	postHashtable.put("post_id", post_id + "");
    }
    
    public void actionLike(int post_id){
    	isPost = true;
    	action = "like";
    	
    	postHashtable.clear();
    	postHashtable.put("post_id", post_id + "");
    }
    
    public void actionList(){
    	isPost = false;
    	action = "list";
    	
    	getHashtable.clear();
    }
    
    public void actionStoreList(int store_id){
    	isPost = false;
    	action = "store_list";
    	
    	getHashtable.clear();
    	getHashtable.put("store_id",store_id + "");
    }
    
    public void actionUserList(int user_id){
    	isPost = false;
    	action = "user_list";
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
    }
    
    public void actionMyList(){
    	isPost = false;
    	action = "my_list";
    	
    	getHashtable.clear();
    }
    
    public void actionNearbyList(double lat_ne, double lat_sw, double lng_sw, double lng_ne){
    	isPost = false;
    	action = "nearby_list";
    	
    	getHashtable.clear();
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    }
    
    public void actionRegionList(){
    	
    }
    
    public ArrayList<MatjiData> request() throws MatjiException {
    	parser = new PostParser();
    	SimpleHttpResponse response = 
    		(isPost) ? 
    				requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
    				:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable); 
	
    	String resultBody = response.getHttpResponseBodyAsString();
    	String resultCode = response.getHttpStatusCode() + "";

    	Log.d("Matji", "PostresultresultBody: " + resultBody);
		Log.d("Matji", "PostresultresultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
    }
}