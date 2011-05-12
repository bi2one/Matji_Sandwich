package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.StoreParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class StoreHttpRequest extends HttpRequest {
    private Hashtable<String, String> getHashtable;
    private Hashtable<String, Object> postHashtable;
    private MatjiDataParser parser;
    private String action;
    private boolean isPost;

    public StoreHttpRequest() {
    	getHashtable = new Hashtable<String, String>();
    	postHashtable = new Hashtable<String, Object>();
    }

    public void actionCount(float lat_sw, float lat_ne, float lng_sw, float lng_ne, String type){
    	isPost = false;
    	action="count";
    	
    	getHashtable.clear();
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    	getHashtable.put("type", type);
    }

    public void actionShow(int store_id){
    	isPost = false;
    	action = "show";
    		
    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    }
    
    public void actionNew(String name, String address, float lat, float lng){
    	isPost = true;
    	action = "new";

    	postHashtable.clear();
    	postHashtable.put("name", name);
    	postHashtable.put("address", address);
    	postHashtable.put("lat", lat);
    	postHashtable.put("lng", lng);
    }
    
    public void actionModify(String name, String address, float lat, float lng, int store_id){
    	isPost = true;
    	action = "modify";
    	
    	postHashtable.clear();
    	postHashtable.put("name", name);
    	postHashtable.put("address", address);
    	postHashtable.put("lat", lat);
    	postHashtable.put("lng", lng);
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionUnLike(int store_id){
    	isPost = true;
    	action = "unlike";
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionLike(int store_id){
    	isPost = true;
    	action = "like";
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionBookmark(int store_id){
    	isPost = true;
    	action = "bookmark";
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionUnBookmark(int store_id){
    	isPost = true;
    	action = "unbookmark";
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionList(){
    	isPost = false;
    	action = "list";
    	
    	getHashtable.clear();
    }

    public void actionNearbyList(float lat_sw, float lat_ne, float lng_sw, float lng_ne){
    	isPost = false;
    	action = "nearby_list";
    	
    	getHashtable.clear();
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    }
    
    public void actionBookmarkedList(int user_id){
    	isPost = false;
    	action = "bookmarked_list";
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
    }
    
    public void actionDetailList(int store_id){
    	isPost = false;
    	action = "detail_list";
    	
    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    }
    
    public void actionDetailNew(int store_id, String note){
    	isPost = true;
    	action = "detail_new";
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    	postHashtable.put("note", note);
    }
    
    public void actionRollbackDetail(int store_detail_info_id){
    	isPost = true;
    	action = "rollback_detail";
    		
    	postHashtable.clear();
    	postHashtable.put("store_detail_info_id", store_detail_info_id);
    }
    
    
    public ArrayList<MatjiData> request() throws MatjiException {
    	SimpleHttpResponse response = 
			(isPost) ? 
					requestHttpResponsePost(serverDomain + "comments/" + action + ".json?", null, postHashtable)
					:requestHttpResponseGet(serverDomain + "comments/" + action + ".json?", null, getHashtable); 
	
    	String resultBody = response.getHttpResponseBodyAsString();
    	String resultCode = response.getHttpStatusCode() + "";
	
    	return parser.getData(resultBody);
    }
}