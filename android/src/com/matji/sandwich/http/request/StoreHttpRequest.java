package com.matji.sandwich.http.request;

import android.content.Context;

import com.matji.sandwich.http.parser.SearchResultParser;
import com.matji.sandwich.http.parser.StoreParser;

public class StoreHttpRequest extends HttpRequest {
    public StoreHttpRequest(Context context) {
    	super(context);
    	controller = "stores";
    }

    public void actionSearch(String keyword, int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "search";
    	parser = new SearchResultParser(new StoreParser());
    	
    	getHashtable.clear();
    	getHashtable.put("q",keyword);
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + "");
    	getHashtable.put("include", "attach_file,user,tags,foods");
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
    	getHashtable.put("include", "attach_file,user,tags,foods");
    }
    
    public void actionNew(String name, String address, double lat, double lng,
			  String add_address, String tel, String website, String cover) {
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	parser = new StoreParser();

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

    public void actionNew(String name, String address, double lat, double lng,
			  String add_address, String tel) {
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "new";
    	parser = new StoreParser();

    	postHashtable.clear();
    	postHashtable.put("name", name);
    	postHashtable.put("address", address);
    	postHashtable.put("lat", lat);
    	postHashtable.put("lng", lng);
    	postHashtable.put("add_address", add_address);
    	postHashtable.put("tel", tel);
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
        getHashtable.put("include", "attach_file,user,tags,foods");
    }    
    
    public void actionDiscoverList(int user_id, int page, int limit){
        httpMethod = HttpMethod.HTTP_GET;
        action = "discover_list";
        parser = new StoreParser();
        
        getHashtable.clear();
        getHashtable.put("page", page + "");
        getHashtable.put("limit", limit + "");
        getHashtable.put("user_id", user_id + "");
        getHashtable.put("include", "attach_file,user,tags,foods");
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
    	getHashtable.put("order", "like_count DESC");
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + ""); 
    	getHashtable.put("include", "attach_file,user,tags,foods");
    }

    public void actionBookmarkList(int page, int limit){
        httpMethod = HttpMethod.HTTP_GET;
        action = "bookmark_list";
        parser = new StoreParser();
        
        getHashtable.clear();
        getHashtable.put("page", page + "");
        getHashtable.put("limit", limit + "");
        getHashtable.put("include", "attach_file,user,tags,foods");
    }
    
    public void actionBookmarkList(int user_id, int page, int limit) {
    	actionBookmarkList(page, limit);
    	getHashtable.put("user_id", user_id+ "");
    }
    
    public void actionLikeList(int user_id, int page, int limit) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "like_list";
    	parser = new StoreParser();

    	getHashtable.clear();
    	getHashtable.put("user_id", user_id+ "");
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + "");
    	getHashtable.put("include", "attach_file,user,tags,foods");
    }

    public void actionNearbyBookmarkedList(int user_id, double lat_sw, double lat_ne, double lng_sw, double lng_ne, int page) {
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "nearby_bookmark_list";
    	parser = new StoreParser();

    	getHashtable.clear();
    	getHashtable.put("user_id", user_id+ "");
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("order", "like_count DESC");
    	getHashtable.put("page", page + "");
    	// getHashtable.put("limit", limit + ""); 
    	getHashtable.put("include", "attach_file,user,tags,foods");
    }
}