package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.StoreParser;
import com.matji.sandwich.session.Session;

import android.content.Context;
import android.util.Log;

public class StoreHttpRequest extends HttpRequest {
    public StoreHttpRequest(Context context) {
    	super(context);
    	controller = "stores";
    }

    public void actionSearch(String keyword, int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "search";
    	parser = new StoreParser(context);
    	
    	getHashtable.clear();
    	getHashtable.put("q",keyword);
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + ""); 
    	getHashtable.put("include", "attach_file,user,tags,store_foods,foods");
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
    	parser = new StoreParser(context); 

    	getHashtable.clear();
    	getHashtable.put("store_id", store_id + "");
    	getHashtable.put("include", "attach_file,user,tags,store_foods,foods");
    }
    
    public void actionNew(String name, String address, double lat, double lng,
			  String add_address, String tel, String website, String cover) {
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	parser = new StoreParser(context);

    	postHashtable.clear();
    	postHashtable.put("name", name);
    	postHashtable.put("address", address);
    	postHashtable.put("lat", lat);
    	postHashtable.put("lng", lng);
    	postHashtable.put("add_address", add_address);
    	postHashtable.put("tel", tel);
    	postHashtable.put("website", website);
    	postHashtable.put("cover", cover);
    }
    
    public void actionModify(String name, String address, int lat, int lng, int store_id) {
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "modify";
    	parser = new StoreParser(context);

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
    	parser = new StoreParser(context);
    	
    	getHashtable.clear();
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + "");
    	getHashtable.put("include", "attach_file,user,tags,store_foods,foods");
    }

    public void actionNearbyList(double lat_sw, double lat_ne, double lng_sw, double lng_ne, int page, int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "nearby_list";
    	parser = new StoreParser(context);

    	getHashtable.clear();
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("order", "like_count DESC,reg_user_id ASC");
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + ""); 
    	getHashtable.put("include", "attach_file,user,tags,store_foods,foods");
    }
    
    public void actionBookmarkedList(int user_id, int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "bookmarked_list";
    	parser = new StoreParser(context);

    	getHashtable.clear();
    	getHashtable.put("user_id", user_id+ "");
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + "");
    	getHashtable.put("include", "attach_file,user,tags,store_foods,foods");
    }

    public void actionCurrentUserBookmarkedList(int page, int limit) {
	Session session = Session.getInstance(context);
	actionBookmarkedList(session.getCurrentUser().getId() , page, limit);
    }

    public void actionNearbyBookmarkedList(int user_id, double lat_sw, double lat_ne, double lng_sw, double lng_ne, int page) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "nearby_bookmark_list";
    	parser = new StoreParser(context);

    	getHashtable.clear();
    	getHashtable.put("user_id", user_id+ "");
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("order", "like_count DESC,reg_user_id ASC");
    	getHashtable.put("page", page + "");
    	// getHashtable.put("limit", limit + ""); 
    	getHashtable.put("include", "attach_file,user,tags,store_foods,foods");
    }
}