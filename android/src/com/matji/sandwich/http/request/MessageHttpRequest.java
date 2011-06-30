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
    	parser = new MessageParser(context);
    	controller = "messages";
    }
    
    public void actionNew(int received_user_id, String message){
    	parser = new MessageParser(context);
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	
    	postHashtable.clear();
		postHashtable.put("received_user_id",received_user_id+"");
		postHashtable.put("message",message);
    }

    public void actionDelete(int message_id){
    	parser = new MessageParser(context);
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "delete";
    	
    	postHashtable.clear();
    	postHashtable.put("message_id", message_id+"");
    }

    public void actionShow(int message_id){
    	parser = new MessageParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "show";
    	
    	getHashtable.clear();
    	getHashtable.put("message_id", message_id+"");
    }
    
    public void actionThreadList(int page, int limit){
    	parser = new MessageParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "thread_list";
    	
    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionChat(int thread_id, int page, int limit){
    	parser = new MessageParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "chat";
    	
    	getHashtable.clear();
    	getHashtable.put("thread_id", thread_id+"");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionList(int page, int limit){
    	parser = new MessageParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "list";

    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }

    public void actionReceivedList(int page, int limit){
    	parser = new MessageParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "received_list";
    	
    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }

    public void actionSentList(int page, int limit){
    	parser = new MessageParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "sent_list";
    	
    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ? 
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";

	Log.d("Matji", "MessageHttpRequest resultBody: " + resultBody);
	Log.d("Matji", "MessageHttpRequest resultCode: " + resultCode);
	
	return parser.parseToMatjiDataList(resultBody);
    }
}