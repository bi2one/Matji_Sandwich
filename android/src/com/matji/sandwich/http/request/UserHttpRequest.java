package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.UserParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class UserHttpRequest extends HttpRequest {
	private MatjiDataParser parser;
	private String action;
	private String controller;

	public UserHttpRequest(Context context) {
		super(context);
		controller = "users";
	}

	public void actionList(int page, int limit){
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";
		parser = new UserParser();

		getHashtable.clear();
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
	}

	public void actionList(int page, int limit, boolean requestPosts, boolean requestStores, boolean requestFiles) {
		actionList(page, limit);
		if (requestPosts) getHashtable.put("include", "posts"); 
		if (requestStores) getHashtable.put("include", "stores"); 
		if (requestFiles) getHashtable.put("include", "attach_files"); 
	}

	public void actionShow(int user_id){
		httpMethod = HttpMethod.HTTP_GET;
		action = "show";
		parser = new UserParser();

		getHashtable.clear();
		getHashtable.put("user_id", user_id + "");
	}

	public void actionShow(int user_id, boolean requestPosts, boolean requestStores, boolean requestFiles) {
		actionShow(user_id);
		if (requestPosts) getHashtable.put("include", "posts"); 
		if (requestStores) getHashtable.put("include", "stores"); 
		if (requestFiles) getHashtable.put("include", "attach_files"); 
	}

	public void actionRankingList() {
		httpMethod = HttpMethod.HTTP_GET;
		action = "ranking_list";
		parser = new UserParser();

		getHashtable.clear();
	}

	public void actionNearByRankingList(int lat_sw, int lat_ne, int lng_sw, int lng_ne) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "nearby_ranking_list";
		parser = new UserParser();

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

	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ? 
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable); 

					String resultBody = response.getHttpResponseBodyAsString();
					String resultCode = response.getHttpStatusCode() + "";

					Log.d("Matji", "UserHttpRequest resultBody: " + resultBody);
					Log.d("Matji", "UserHttpRequest resultCode: " + resultCode);

					return parser.parseToMatjiDataList(resultBody);
	}
}