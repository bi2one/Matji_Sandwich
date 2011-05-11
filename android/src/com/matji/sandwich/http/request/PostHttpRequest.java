package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.PostParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class PostHttpRequest extends HttpRequest {
    private Hashtable<String, String> getHashtable;
    private Hashtable<String, Object> postHashtable;
    private MatjiDataParser parser;
    private boolean isPost;
    private float lat_sw,lat_ne,lng_sw,lng_ne;
    private int post_id;
    private int store_id;
    private int user_id;
    private String post;
    private String action;
    private String access_token;
    
    public PostHttpRequest() {
	parser = new PostParser();
	getHashtable = new Hashtable<String, String>();
	postHashtable = new Hashtable<String, Object>();
	initParam();
    }

    public void actionShow(int post_id){
    	getHashtable.clear();
    	this.isPost = false;
    	this.post_id = post_id;
    	getHashtable.put("post_id", post_id + "");
    }
    
    public void actionNew(String post){
    	postHashtable.clear();
    	this.isPost = true;
    	this.post = post;
    	postHashtable.put("post", post);
    }
    
    public void actionDelete(int post_id){
    	postHashtable.clear();
    	this.isPost = true;
    	this.post_id = post_id;
    	postHashtable.put("post_id",post_id + "");
    }
    
    public void actionUnlike(int post_id){
    	postHashtable.clear();
    	this.isPost = true;
    	this.post_id = post_id;
    	postHashtable.put("post_id", post_id + "");
    }
    
    public void actionLike(int post_id){
    	postHashtable.clear();
    	this.isPost = true;
    	this.post_id = post_id;
    	postHashtable.put("post_id", post_id + "");
    }
    
    public void actionList(){
    	getHashtable.clear();
    	this.isPost = false;
    }
    
    public void actionStoreList(int store_id){
    	getHashtable.clear();
    	this.isPost = false;
    	this.store_id = store_id;
    	getHashtable.put("store_id",store_id + "");
    }
    
    public void actionUserList(int user_id){
    	getHashtable.clear();
    	this.isPost = false;
    	this.user_id = user_id;
    	getHashtable.put("user_id", user_id + "");
    }
    
    public void actionMyList(){
    	getHashtable.clear();
    	this.isPost = false;
    }
    
    public void actionNearbyList(float lat_ne, float lat_sw, float lng_sw, float lng_ne){
    	getHashtable.clear();
    	this.isPost = false;
    	this.lat_ne = lat_ne;
    	this.lat_sw = lat_sw;
    	this.lng_ne = lng_ne;
    	this.lng_sw = lng_sw;
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    }
    
    public void actionRegionList(){
    	
    }
    
    public void initParam() {
    	lat_sw = lat_ne = lng_sw = lng_ne = 0;
    }

    public ArrayList<MatjiData> request() throws MatjiException {
    SimpleHttpResponse response = requestHttpResponseGet(serverDomain + "posts/" + action + ".json", null, getHashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";

	return parser.getData(resultBody);
    }
}