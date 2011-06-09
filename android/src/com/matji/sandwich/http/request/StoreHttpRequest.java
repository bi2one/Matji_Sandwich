package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.StoreParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class StoreHttpRequest extends HttpRequest {
    private MatjiDataParser parser;
    private String action;
    private String controller;
    
    public StoreHttpRequest(Context context) {
    	super(context);
    	controller = "stores";
    }

    public void actionCount(int lat_sw, int lat_ne, int lng_sw, int lng_ne, String type){
    	httpMethod = HttpMethod.HTTP_GET;
    	action="count";
    	
    	getHashtable.clear();
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    	getHashtable.put("type", type);
    }

    public void actionShow(int store_id){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "show";
    	parser = new StoreParser(); 
    		
    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    	getHashtable.put("include", "attach_file,user,store_foods,tags");
    }
    
    public void actionNew(String name, String address, int lat, int lng){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	parser = new StoreParser();

    	postHashtable.clear();
    	postHashtable.put("name", name);
    	postHashtable.put("address", address);
    	postHashtable.put("lat", lat);
    	postHashtable.put("lng", lng);
    }
    
    public void actionModify(String name, String address, int lat, int lng, int store_id) {
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "modify";
    	parser = new StoreParser();

    	postHashtable.clear();
    	postHashtable.put("name", name);
    	postHashtable.put("address", address);
    	postHashtable.put("lat", lat);
    	postHashtable.put("lng", lng);
    	postHashtable.put("store_id", store_id);
    }
    
    public void actionList(int page, int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "list";
    	parser = new StoreParser();
    	
    	getHashtable.clear();
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + "");
    	getHashtable.put("include", "attach_file,user,tags");
    }

    public void actionNearbyList(double lat_sw, double lat_ne, double lng_sw, double lng_ne, int page, int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "nearby_list";
    	parser = new StoreParser();

    	getHashtable.clear();
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + ""); 
    	getHashtable.put("include", "attach_file,user");
    	
    }
    
    public void actionBookmarkedList(){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "bookmarked_list";
    	parser = new StoreParser();

    	getHashtable.clear();
    	getHashtable.put("include", "attach_file,user");
    }
    
    public void actionDetailList(int store_id, int page, int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "detail_list";
    	parser = new StoreParser();
    	
    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + "");
    }
    
    public void actionDetailNew(int store_id, String note){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "detail_new";
    	parser = new StoreParser();
    	
    	postHashtable.clear();
    	postHashtable.put("store_id", store_id);
    	postHashtable.put("note", note);
    }
    
    public void actionRollbackDetail(int store_detail_info_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "rollback_detail";
    	parser = new StoreParser();
    		
    	postHashtable.clear();
    	postHashtable.put("store_detail_info_id", store_detail_info_id);
    }
    
    
    public ArrayList<MatjiData> request() throws MatjiException {
    	SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ? 
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable); 
	
    	String resultBody = response.getHttpResponseBodyAsString();
    	String resultCode = response.getHttpStatusCode() + "";

    	Log.d("Matji", "StoreHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "StoreHttpRequest resultCode: " + resultCode);

    	return parser.parseToMatjiDataList(resultBody);
    }
}