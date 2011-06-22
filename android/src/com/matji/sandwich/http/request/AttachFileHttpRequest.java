package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.AttachFileParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class AttachFileHttpRequest extends HttpRequest {
	private MatjiDataParser parser;
    private String action;
    private String controller;
    
    public AttachFileHttpRequest(Context context) {
    	super(context);
    	parser = new AttachFileParser(context);
    	controller = "attach_files";
    }

    public void actionUpload(File imageFile, int postId){
    	parser = new AttachFileParser(context);
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "upload.json";
    	
    	postHashtable.clear();
    	
    	postHashtable.put("upload_file", imageFile);
    	postHashtable.put("post_id", postId + "");
    	
    }
    
    public void actionImage(){
    	parser = new AttachFileParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "image";
    	
    	getHashtable.clear();
    }
    
    public void actionList(){
    	parser = new AttachFileParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "list";
    	
    	getHashtable.clear();
    }
    
    public void actionStoreList(int store_id, int page, int limit){
    	parser = new AttachFileParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "store_list";
    	
    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionPostList(int post_id, int page, int limit){
    	parser = new AttachFileParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "post_list";
    	
    	getHashtable.clear();
    	getHashtable.put("post_id", post_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionUserList(int user_id, int page, int limit) {
    	parser = new AttachFileParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "user_list";
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
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

		Log.d("Matji", "AttachFileHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "AttachFileHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
    }
}