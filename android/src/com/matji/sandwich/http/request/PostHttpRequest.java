package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.PostParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class PostHttpRequest extends HttpRequest {
    public PostHttpRequest(Context context) {
    	super(context);
		parser = new PostParser(context);
		controller = "posts";
    }

    public void actionShow(int post_id){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "show";
    	parser = new PostParser(context);
    	
    	getHashtable.clear();
    	getHashtable.put("post_id", post_id + "");
    	getHashtable.put("include", "user,store,tags");
    }
    
    public void actionNew(String post, String tags, String from_where, double lat, double lng) {
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	parser = new PostParser(context);
    	
    	postHashtable.clear();
    	postHashtable.put("post", post);
    	postHashtable.put("tags", tags);
    	postHashtable.put("from_where", from_where);
    	postHashtable.put("lat", lat + "");
    	postHashtable.put("lng", lng + "");
    }
    
    public void actionNew(String post, String tags, String from_where, int store_id) {
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	parser = new PostParser(context);
    	
    	postHashtable.clear();
    	postHashtable.put("post", post);
    	postHashtable.put("tags", tags);
    	postHashtable.put("from_where", from_where);
    	postHashtable.put("store_id", store_id + "");
    }
    
    public void actionDelete(int post_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "delete";
    	
    	postHashtable.clear();
    	postHashtable.put("post_id",post_id + "");
    }
    
    public void actionList(int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;	
    	action = "list";
    	parser = new PostParser(context);
    	
    	getHashtable.clear();
    	getHashtable.put("page", "" + page);
    	getHashtable.put("limit", "" + limit);
    	getHashtable.put("include", "user,store,tags");
    }    
    
    public void actionStoreList(int store_id, int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "store_list";
    	parser = new PostParser(context);
    	
    	getHashtable.clear();
    	getHashtable.put("store_id",store_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    	getHashtable.put("include", "user,store,tags");
    }    
    
    public void actionUserList(int user_id, int page, int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "user_list";
    	parser = new PostParser(context);
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    	getHashtable.put("include", "user,store,tags");
    }
    
    public void actionMyList(int page , int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "my_list";
    	parser = new PostParser(context);
    	
    	getHashtable.clear();
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    	getHashtable.put("include", "user,store,tags");
    }
    
    public void actionNearbyList(double lat_ne, double lat_sw, double lng_sw, double lng_ne, int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "nearby_list";
    	parser = new PostParser(context);
    	
    	getHashtable.clear();
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    	getHashtable.put("include", "user,store,tags");
    }

    public void actionSearch(String keyword, int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "search";
    	parser = new PostParser(context);
    	
    	getHashtable.clear();
    	getHashtable.put("q", keyword);
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");
    	getHashtable.put("include", "user,store,tags");
    }
}
