package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.TagParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class TagHttpRequest extends HttpRequest {
	//	private boolean isPost; // tag �����  GET 諛⑹�..?

	public TagHttpRequest(Context context) {
		super(context);
		controller = "tags";
	}

	public void actionShow(int tag_id) {
		action = "show";
		parser = new TagParser(context);
		
		getHashtable.clear();
		getHashtable.put("tag_id", tag_id + "");
	}
	
	public void actionList() {
		action = "list";
		parser = new TagParser(context);
		
		getHashtable.clear();
	}
	
	public void actionStoreTagList(int store_id, int page, int limit) {
		action = "store_tag_list";
		parser = new TagParser(context);
		
		getHashtable.clear();
		getHashtable.put("store_id", store_id + "");
		getHashtable.put("page", page + "");
		getHashtable.put("limit", limit + "");
	}
	
	public void actionUserTagList(int user_id, int page, int limit) {
		action = "user_tag_list";
		parser = new TagParser(context);
		
		getHashtable.clear();
		getHashtable.put("user_id", user_id+ "");		
		getHashtable.put("page", page+ "");
		getHashtable.put("limit", limit+ "");
	}
	
	public void actionPostTagList(int post_id) {
		action = "post_tag_list";
		parser = new TagParser(context);
		
		getHashtable.clear();
		getHashtable.put("post_id", post_id + "");
	}
}