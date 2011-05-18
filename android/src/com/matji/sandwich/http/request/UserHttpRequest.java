package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.UserParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class UserHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
    private MatjiDataParser parser;
    private String action;
    private boolean isPost;
    private String controller;
    
    public UserHttpRequest() {
    	getHashtable = new Hashtable<String, String>();
    	postHashtable = new Hashtable<String, Object>();
    	controller = "users";
    }

    public void actionList(){
    	isPost = false;
    	action = "list";
    	parser = new UserParser();

    	getHashtable.clear();
    }
    
    public void actionShow(int user_id){
    	isPost = false;
    	action = "show";
    	parser = new UserParser();

    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
    }

    public void actionMe() {
    	
    }
    
    public void actionAuthorize() {
    	
    }
   
    public void actionCreate() {
    	
    }
    
    public void actionProfile() {
    	
    }
    
    public void actionUpdate(){
    	
    }

    public ArrayList<MatjiData> request() throws MatjiException {
    	SimpleHttpResponse response = 
			(isPost) ? 
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable); 

    	String resultBody = response.getHttpResponseBodyAsString();
    	String resultCode = response.getHttpStatusCode() + "";

    	Log.d("Matji", "UserHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "UserHttpRequest resultCode: " + resultCode);

    	return parser.parseToMatjiDataList(resultBody);
    }
}