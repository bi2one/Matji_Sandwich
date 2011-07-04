package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.UserParser;

import android.content.Context;

public class UserHttpRequest extends HttpRequest {
	public UserHttpRequest(Context context) {
		super(context);
		controller = "users";
	}

	public void actionList(int page, int limit){
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";
		parser = new UserParser(context);

		getHashtable.clear();
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
		getHashtable.put("include", "stores,attach_files,user_mileage"); 
	}

	public void actionShow(int user_id){
		httpMethod = HttpMethod.HTTP_GET;
		action = "show";
		parser = new UserParser(context);

		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
		getHashtable.put("include", "stores,attach_files,user_mileage");
	}

	public void actionRankingList() {
		httpMethod = HttpMethod.HTTP_GET;
		action = "ranking_list";
		parser = new UserParser(context);

		getHashtable.clear();
	}

	public void actionNearByRankingList(int lat_sw, int lat_ne, int lng_sw, int lng_ne) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "nearby_ranking_list";
		parser = new UserParser(context);

		getHashtable.clear();
		getHashtable.put("lat_ne", lat_ne + "");
		getHashtable.put("lat_sw", lat_sw + "");
		getHashtable.put("lng_ne", lng_ne + "");
		getHashtable.put("lng_sw", lng_sw + "");
	}

	public void actionMe() {

	}

	public void actionAuthorize() {

	}

	public void actionCreate() {

	}

	public void actionProfile() {

	}

	public void actionUpdate(){

	}
}