package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class UserHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
    private MatjiDataParser parser;
    private String action;
    private boolean isPost;

    public UserHttpRequest() {
    	getHashtable = new Hashtable<String, String>();
    	postHashtable = new Hashtable<String, Object>();
    }

    public void actionList(){
    	isPost = false;
    	action = "list";
    	
    	getHashtable.clear();
    }
    
    public void actionShow(int user_id){
    	isPost = false;
    	action = "show";
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
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