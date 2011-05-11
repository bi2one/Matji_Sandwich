package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.AttachFileParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class AttachFileHttpRequest extends HttpRequest {
    private Hashtable<String, String> hashtable;
    private int post_id ;
    private int store_id ;
    private String action ;
    private MatjiDataParser parser;
    
    public AttachFileHttpRequest() {
	parser = new AttachFileParser();
	hashtable = new Hashtable<String, String>();
	initParam();
    }

    public void setStoreId(int store_id){
    	this.store_id = store_id;
    }

    public void setPostId(int post_id){
    	this.post_id = post_id;
    }
    
    public void setAction(String action){
    	this.action = action;
    }

    public void initParam() {
    	store_id = 0;
    	post_id = 0;
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	hashtable.clear();
	hashtable.put("store_id", store_id + "");
	hashtable.put("post_id", post_id + "");
	
	SimpleHttpResponse response = requestHttpResponseGet(serverDomain + "attach_files/"+ action + ".json", null, hashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	Log.d("asdfsaf",resultBody);
	return parser.getData(resultBody);
    }
}