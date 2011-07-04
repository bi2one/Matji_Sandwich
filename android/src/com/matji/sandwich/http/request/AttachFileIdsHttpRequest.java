package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.AttachFileParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.adapter.ImageAdapter;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.AttachFileIds;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class AttachFileIdsHttpRequest extends HttpRequest {
    private int capacity;
    
    public AttachFileIdsHttpRequest(Context context, int capacity) {
    	super(context);
    	this.capacity = capacity;
    	parser = new AttachFileParser(context);
    	controller = "attach_files";
    }

    public void actionStoreList(int store_id, int page, int limit) {
    	parser = new AttachFileParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "store_list";
    	
    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionPostList(int store_id, int page, int limit) {
    	parser = new AttachFileParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "post_id";
    	
    	getHashtable.clear();
    	getHashtable.put("post_id", store_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public void actionUserList(int store_id, int page, int limit) {
    	parser = new AttachFileParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "user_list";
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", store_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    }
    
    public ArrayList<MatjiData> request() throws MatjiException {
    	SimpleHttpResponse response = 
    		(httpMethod == HttpMethod.HTTP_POST) ? 
	    requestHttpResponsePost(null, postHashtable)
    				:requestHttpResponseGet(null, getHashtable);
    	
	
   		String resultBody = response.getHttpResponseBodyAsString();
   		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "AttachFileHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "AttachFileHttpRequest resultCode: " + resultCode);

		ArrayList<MatjiData> attachFiles = parser.parseToMatjiDataList(resultBody);
		ArrayList<MatjiData> result = new ArrayList<MatjiData>();
		
		for (int i = 0; i < attachFiles.size(); i = i+capacity){
			int[] ids = new int[capacity];
			
			for (int j = 0; j < ids.length; j++){
				ids[j] = ImageAdapter.IMAGE_IS_NULL;
			}
			
			for (int j = 0; (j < ids.length) && (i + j < attachFiles.size()); j++) {
				ids[j] = ((AttachFile) attachFiles.get(i + j)).getId();
			}
			
			AttachFileIds attachFileIds = new AttachFileIds();
			attachFileIds.setIds(ids);
			result.add(attachFileIds);
		}

		return result;
    }
}