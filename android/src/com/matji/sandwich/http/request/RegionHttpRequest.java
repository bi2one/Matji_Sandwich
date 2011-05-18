package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.RegionParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class RegionHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
    private MatjiDataParser parser;
    private String action;
    private String controller;
    private boolean isPost;
    
    public RegionHttpRequest() {
    	getHashtable = new Hashtable<String, String>();
    	postHashtable = new Hashtable<String, Object>();
    	parser = new RegionParser();
    	controller = "regions";
    }

    public void actionBookmark(double lat_sw, double lat_ne, double lng_sw, double lng_ne, String description){
    	isPost = true;
    	action = "bookmark";
    	parser = new RegionParser();
    	
    	postHashtable.clear();
    	postHashtable.put("lat_sw",lat_sw);
    	postHashtable.put("lat_ne",lat_ne);
    	postHashtable.put("lng_sw",lng_sw);
    	postHashtable.put("lng_ne",lng_ne);
    }
    
    public void actionUnBookmark(int region_id){
    	isPost = true;
    	action = "unbookmark";
//    	
    	postHashtable.clear();
    	postHashtable.put("region_id", region_id);
    }
    
    public void actionBookmarkedList(){
    	isPost = false;
    	action = "bookmarked_list";
      	parser = new RegionParser();
      	
    	getHashtable.clear();
    }
    
    public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(isPost) ?
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "RegionHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "RegionHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
    }
}