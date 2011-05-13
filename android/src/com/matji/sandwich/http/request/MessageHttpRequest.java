package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MessageParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class MessageHttpRequest extends HttpRequest {
	private Hashtable<String, String> getHashtable;
	private Hashtable<String, Object> postHashtable;
    private MatjiDataParser parser;
	private String action;
    private boolean isPost;

    public MessageHttpRequest() {
    	getHashtable = new Hashtable<String, String>();
    	postHashtable = new Hashtable<String, Object>();
    }
    
    public void actionNew(String received_user_id, String message){
    	isPost = true;
    	action = "new";
    	parser = new MessageParser();
    	
    	postHashtable.clear();
		postHashtable.put("received_user_id",received_user_id);
		postHashtable.put("message",message);
    }

    public void actionDelete(String message_id){
    	isPost = true;
    	action = "new";

    	
    	postHashtable.clear();
    	postHashtable.put("message_id", message_id);
    }
    
    
    public void actionShow(String message_id){
    	isPost = false;
    	action = "show";
    	parser = new MessageParser();
    	
    	getHashtable.clear();
    	getHashtable.put("message_id", message_id);
    }
    
    public void actionList(){
    	isPost = false;
    	action = "show";
    	parser = new MessageParser();
    	
    	getHashtable.clear();
    }

    public void actionReceivedList(){
    	isPost = false;
    	action = "received_list";
    	parser = new MessageParser();
    	
    	getHashtable.clear();
    }

    public void actionSentList(){
    	isPost = false;
    	action = "sent_list";
    	parser = new MessageParser();
    	
    	getHashtable.clear();
    }
    
    public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(isPost) ? 
					requestHttpResponsePost(serverDomain + "foods/" + action + ".json?", null, postHashtable)
					:requestHttpResponseGet(serverDomain + "foods/" + action + ".json?", null, getHashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";

	Log.d("Matji", "resultBody: " + resultBody);
	Log.d("Matji", "resultCode: " + resultCode);
	
	return parser.parseToMatjiDataList(resultBody);
    }
}