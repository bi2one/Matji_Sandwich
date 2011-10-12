package com.matji.sandwich.http.request;

import java.io.File;

import android.content.Context;

import com.matji.sandwich.http.parser.AttachFileParser;

public class AttachFileHttpRequest extends HttpRequest {
//    private ProgressListener listener;
//    private int progressTag;
    
    public AttachFileHttpRequest(Context context) {
    	super(context);
    	parser = new AttachFileParser();
    	controller = "attach_files";
    }

    public void actionProfileUpload(File imageFile) {
        parser = new AttachFileParser();
        httpMethod = HttpMethod.HTTP_POST;
        action = "profile_upload";
        
        postHashtable.clear();
        postHashtable.put("upload_file", imageFile);
    }
    
    public void actionUpload(File imageFile, int postId){
    	parser = new AttachFileParser();
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "upload.json";
    	
    	postHashtable.clear();
    	
    	postHashtable.put("upload_file", imageFile);
    	postHashtable.put("post_id", postId + "");
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
    
    public void actionStoreList(int store_id, int page, int limit){
    	parser = new AttachFileParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "store_list";
    	
    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionPostList(int post_id, int page, int limit){
    	parser = new AttachFileParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "post_list";
    	
    	getHashtable.clear();
    	getHashtable.put("post_id", post_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionUserList(int user_id, int page, int limit) {
    	parser = new AttachFileParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "user_list";
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
}