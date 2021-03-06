package com.matji.sandwich.http.request;

import android.content.Context;

import com.matji.sandwich.http.parser.PostParser;
import com.matji.sandwich.http.parser.SearchResultParser;

public class PostHttpRequest extends HttpRequest {
	public static enum TagByType {
		STORE, USER,
	}

	public PostHttpRequest(Context context) {
		super(context);
		parser = new PostParser();
		controller = "posts";
	}

	public void actionShow(int post_id){
		httpMethod = HttpMethod.HTTP_GET;
		action = "show";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("post_id", post_id + "");
		getHashtable.put("include", "user,store,tags");
	}

	public void actionNew(String post, String tags, Boolean twitter, Boolean facebook) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "new";
		parser = new PostParser();

		postHashtable.clear();
		postHashtable.put("post", post);
		postHashtable.put("tags", tags);
		postHashtable.put("from_where", "ANDROID");
		postHashtable.put("twitter", twitter);
		postHashtable.put("facebook", facebook);
	}

	public void actionNew(String post, String tags, int store_id, Boolean twitter, Boolean facebook) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "new";
		parser = new PostParser();

		postHashtable.clear();
		postHashtable.put("post", post);
		postHashtable.put("tags", tags);
		postHashtable.put("from_where", "ANDROID");
		postHashtable.put("store_id", store_id + "");
		postHashtable.put("twitter", twitter);
		postHashtable.put("facebook", facebook);
	}

	public void actionDelete(int post_id){
		httpMethod = HttpMethod.HTTP_POST;
		action = "delete";

		postHashtable.clear();
		postHashtable.put("post_id",post_id + "");
	}

	public void actionCountryList(int page, int limit, String countryCode) {
		httpMethod = HttpMethod.HTTP_GET;	
		action = "country_list";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("page", "" + page);
		getHashtable.put("limit", "" + limit);
		getHashtable.put("country_code", "" + countryCode);
		getHashtable.put("include", "user,store,tags");
	}

	public void actionList(int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;	
		action = "list";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("page", "" + page);
		getHashtable.put("limit", "" + limit);
		getHashtable.put("include", "user,store,tags");
	}

	public void actionFriendList(int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "friend_list";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("page", "" + page);
		getHashtable.put("limit", "" + limit);
		getHashtable.put("include", "user,store,tags");
	}

	public void actionUserTagByList(int user_tag_id, int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;   
		action = "usertagby_list";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("page", "" + page);
		getHashtable.put("limit", "" + limit);
		getHashtable.put("user_tag_id", "" + user_tag_id);
		getHashtable.put("include", "user,store,tags");
	}    

	public void actionStoreTagByList(int store_tag_id, int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;   
		action = "storetagby_list";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("page", "" + page);
		getHashtable.put("limit", "" + limit);
		getHashtable.put("store_tag_id", "" + store_tag_id);
		getHashtable.put("include", "user,store,tags");
	}

	public void actionTagByList(TagByType type, int tag_id, int page, int limit) {
		switch (type) {
		case STORE:
			actionStoreTagByList(tag_id, page, limit);
			break;
		case USER:
			actionUserTagByList(tag_id, page, limit);
			break;
		}
	} 

	public void actionStoreList(int store_id, int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "store_list";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("store_id",store_id + "");
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
		getHashtable.put("include", "user,store,tags");
	}    

	public void actionUserList(int user_id, int page, int limit){
		httpMethod = HttpMethod.HTTP_GET;
		action = "user_list";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
		getHashtable.put("include", "user,store,tags");
	}

	public void actionMyList(int page , int limit){
		httpMethod = HttpMethod.HTTP_GET;
		action = "my_list";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
		getHashtable.put("include", "user,store,tags");
	}

	public void actionNearbyList(double lat_ne, double lat_sw, double lng_sw, double lng_ne, int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "nearby_list";
		parser = new PostParser();

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
		parser = new SearchResultParser(new PostParser());

		getHashtable.clear();
		getHashtable.put("q", keyword);
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
		getHashtable.put("include", "user,store,tags");
	}

	public void actionModify(int post_id, String post) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "modify";
		parser = new PostParser();

		getHashtable.clear();
		getHashtable.put("post_id", post_id+"");
		getHashtable.put("post", post);
	}
}
