package com.matji.sandwich.http.request;

import java.util.ArrayList;

import android.content.Context;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.http.parser.MessageParser;

public class MessageHttpRequest extends HttpRequest {
    public MessageHttpRequest(Context context) {
    	super(context);
    	parser = new MessageParser(context);
    	controller = "messages";
    }
    
    public void actionNew(int received_user_id, String message){
        parser = new MessageParser(getContext());
        httpMethod = HttpMethod.HTTP_POST;
        action = "new";
        
        postHashtable.clear();
        postHashtable.put("received_user_id",received_user_id+"");
        postHashtable.put("message",message);
    }
    
    public void actionNew(int[] received_user_ids, String message){
        parser = new MessageParser(getContext());
        httpMethod = HttpMethod.HTTP_POST;
        action = "new";
        
        postHashtable.clear();
        
        String ids = "";
        for (int id : received_user_ids) {
            ids += id + ",";
        }
        
        postHashtable.put("received_user_id",ids);
        postHashtable.put("message",message);
    }

    public void actionDelete(int message_id){
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "delete";
    	
    	postHashtable.clear();
    	postHashtable.put("message_id", message_id+"");
    }
    
    public void actionDeleteThread(int thread_id){
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "delete_thread";
    	
    	postHashtable.clear();
    	postHashtable.put("thread_id", thread_id+"");
    }

    public void actionShow(int message_id){
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "show";
    	
    	getHashtable.clear();
    	getHashtable.put("message_id", message_id+"");
    }
    
    public void actionThreadList(int page, int limit){
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "thread_list";
    	
    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionChat(int thread_id, int page, int limit){
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "chat";
    	
    	getHashtable.clear();
    	getHashtable.put("thread_id", thread_id+"");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionList(int page, int limit){
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "list";

    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }

    public void actionReceivedList(int page, int limit){
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "received_list";
    	
    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }

    public void actionSentList(int page, int limit){
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "sent_list";
    	
    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionRead(ArrayList<MatjiData> messages) {
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "read";
    	
    	String ids = "";
    	for (MatjiData message : messages) {
    		ids += ((Message) message).getId() + ",";
    	}
    	postHashtable.clear();
    	postHashtable.put("message_id", ids);
    }
    
    public void actionRead(int message_id) {
    	parser = new MessageParser(getContext());
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "read";
    	
    	postHashtable.clear();
    	postHashtable.put("message_id", message_id);
    }
}