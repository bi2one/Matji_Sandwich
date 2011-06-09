package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.NoticeParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class NoticeHttpRequest extends HttpRequest {
	private MatjiDataParser parser;
	private String action;
	private String controller;
	
	public NoticeHttpRequest(Context context) {
		super(context);
		parser = new NoticeParser(context);
		controller = "notices";
	}

	public void actionShow(int notice_id) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "show";
		
		getHashtable.clear();
		getHashtable.put("notice_id", notice_id + "");
	}
	
	public void actionList() {
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";

		getHashtable.clear();
	}
	
	public void actionBadge(int last_notice_id, int last_alarm_id) {
		// TODO
	}
	
	public ArrayList<MatjiData> request() throws MatjiException {
		SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ?
					requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable);

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";
		
		Log.d("Matji", "NoticeHttpRequest resultBody: " + resultBody);
		Log.d("Matji", "NoticeHttpRequest resultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}