package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.AttachFileParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.AttachFileParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class AttachFileHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
    private MatjiDataParser parser;
    private boolean isPost;
    private String action;
    private String controller;
    
    public AttachFileHttpRequest() {
    	getHashtable = new Hashtable<String, String>();
    	postHashtable = new Hashtable<String, Object>();
    	parser = new AttachFileParser();
    	controller = "attach_files";
    }

    public void actionUpload(){
    	parser = new AttachFileParser();
    	isPost = true;
    	action = "upload";
    	
    	postHashtable.clear();
    }
    
    public void actionImage(){
    	parser = new AttachFileParser();
    	isPost = false;
    	action = "image";
    	
    	getHashtable.clear();
    }
    
    public void actionList(){
    	parser = new AttachFileParser();
    	isPost = false;
    	action = "list";
    	
    	getHashtable.clear();
    }
    
    public void actionStoreList(int store_id){
    	parser = new AttachFileParser();
    	isPost = false;
    	action = "store_list";
    	
    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    }
    
    public void actionPostList(int post_id){
    	parser = new AttachFileParser();
    	isPost =false;
    	action = "post_list";
    	
    	getHashtable.clear();
    	getHashtable.put("post_id", post_id + "");
    }
    
    public ArrayList<MatjiData> request() throws MatjiException {
    	SimpleHttpResponse response = 
    		(isPost) ? 
    				requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
    				:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable); 
    	
	
   		String resultBody = response.getHttpResponseBodyAsString();
   		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "AttachFileHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "AttachFileHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
    }
}