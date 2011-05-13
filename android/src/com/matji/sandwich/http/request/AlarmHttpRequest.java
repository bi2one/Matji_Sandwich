package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class AlarmHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
	private boolean isPost;
	private String action ;
    private MatjiDataParser parser;

    public AlarmHttpRequest() {
    	getHashtable = new Hashtable<String, String>();
    	postHashtable = new Hashtable<String, Object>();
    }

    public void actionList(int user_id){
    	isPost = false;
    	action = "list";
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	SimpleHttpResponse response = 
		(isPost) ? 
				requestHttpResponsePost(serverDomain + "alarms/" + action + ".json?", null, postHashtable)
				:requestHttpResponseGet(serverDomain + "alarms/" + action + ".json?", null, getHashtable); 
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	return parser.parseToMatjiDataList(resultBody);
    }
}