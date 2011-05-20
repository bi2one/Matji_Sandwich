package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.AttachFileParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class AttachFileHttpRequest extends HttpRequest {
	private MatjiDataParser<AttachFile> parser;
    private String action;
    private String controller;
    
    public AttachFileHttpRequest(Context context) {
    	super(context);
    	parser = new AttachFileParser();
    	controller = "attach_files";
    }

    public void actionUpload(){
    	parser = new AttachFileParser();
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "upload";
    	
    	postHashtable.clear();
    }
    
    public void actionImage(){
    	parser = new AttachFileParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "image";
    	
    	getHashtable.clear();
    }
    
    public void actionList(){
    	parser = new AttachFileParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "list";
    	
    	getHashtable.clear();
    }
    
    public void actionStoreList(int store_id){
    	parser = new AttachFileParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "store_list";
    	
    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    }
    
    public void actionPostList(int post_id){
    	parser = new AttachFileParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "post_list";
    	
    	getHashtable.clear();
    	getHashtable.put("post_id", post_id + "");
    }
    
    public ArrayList<AttachFile> request() throws MatjiException {
    	SimpleHttpResponse response = 
    		(httpMethod == HttpMethod.HTTP_POST) ? 
    				requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
    				:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable); 
    	
	
   		String resultBody = response.getHttpResponseBodyAsString();
   		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "AttachFileHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "AttachFileHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
    }
}