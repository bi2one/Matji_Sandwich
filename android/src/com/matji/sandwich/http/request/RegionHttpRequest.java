package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.RegionParser;

import android.content.Context;

public class RegionHttpRequest extends HttpRequest {
    public RegionHttpRequest(Context context) {
    	super(context);
    	parser = new RegionParser(context);
    	controller = "regions";
    }

    public void actionBookmark(double lat_sw, double lat_ne, double lng_sw, double lng_ne, String description){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "bookmark";
    	parser = new RegionParser(context);

    	postHashtable.clear();
    	postHashtable.put("lat_sw",lat_sw);
    	postHashtable.put("lat_ne",lat_ne);
    	postHashtable.put("lng_sw",lng_sw);
    	postHashtable.put("lng_ne",lng_ne);
	postHashtable.put("description", description);
    }
    
    public void actionUnBookmark(int region_id){
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "unbookmark";
	
    	postHashtable.clear();
    	postHashtable.put("region_id", region_id);
    }
    
    public void actionBookmarkedList(int page, int limit){
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "bookmarked_list";
      	parser = new RegionParser(context);
      	
    	getHashtable.clear();
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + "");
    }
}