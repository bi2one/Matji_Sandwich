package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.NoticeParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class NoticeHttpRequest extends HttpRequest {
	private Hashtable<String, Object> postHashtable;
	private Hashtable<String, String> getHashtable;
	private MatjiDataParser parser;
	private String action;
	private boolean isPost;
	private String controller;
	
	public NoticeHttpRequest() {
		postHashtable = new Hashtable<String, Object>();
		getHashtable = new Hashtable<String, String>();
		parser = new NoticeParser();
		controller = "notices";
	}

	public void actionShow(int notice_id) {
		isPost = false;
		action = "show";
		
		getHashtable.clear();
		getHashtable.put("notice_id", notice_id + "");
	}
	
	public void actionList() {
		isPost = false;
		action = "list";

		getHashtable.clear();
	}
	
	public void actionBadge(int last_notice_id, int last_alarm_id) {
		// TODO
	}
	
	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(isPost) ?
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";
		
		Log.d("Matji", "NoticeHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "NoticeHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}