package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MessageParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class MessageHttpRequest extends HttpRequest {
	private MatjiDataParser parser;
	protected String action;
    protected String controller;
    
    public MessageHttpRequest(Context context) {
    	super(context);
    	parser = new MessageParser();
    	controller = "messages";
    }
    
    public void actionNew(String received_user_id, String message){
    	parser = new MessageParser();
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	
    	postHashtable.clear();
		postHashtable.put("received_user_id",received_user_id);
		postHashtable.put("message",message);
    }

    public void actionDelete(String message_id){
    	parser = new MessageParser();
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	
    	postHashtable.clear();
    	postHashtable.put("message_id", message_id);
    }

    public void actionShow(String message_id){
    	parser = new MessageParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "show";
    	
    	getHashtable.clear();
    	getHashtable.put("message_id", message_id);
    }
    
    public void actionTreadList(){
    	parser = new MessageParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "thread_list";
    	
    	getHashtable.clear();
    }
    
    public void actionChat(String thread_id){
    	parser = new MessageParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "chat";
    	
    	postHashtable.clear();
    	postHashtable.put("thread_id", thread_id);
    }
    
    public void actionList(){
    	parser = new MessageParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "list";

    	getHashtable.clear();
    }

    public void actionReceivedList(){
    	parser = new MessageParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "received_list";
    	
    	getHashtable.clear();
    }

    public void actionSentList(){
    	parser = new MessageParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "sent_list";
    	
    	getHashtable.clear();
    }
    
    public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ? 
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";

	Log.d("Matji", "MessageresultBody: " + resultBody);
	Log.d("Matji", "MessageresultCode: " + resultCode);
	
	return parser.parseToMatjiDataList(resultBody);
    }
}