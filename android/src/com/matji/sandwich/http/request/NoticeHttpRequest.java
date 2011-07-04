package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.NoticeParser;

import android.content.Context;

public class NoticeHttpRequest extends HttpRequest {
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
	
	public void actionList(int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";

		getHashtable.clear();
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
	}
	
	public void actionBadge(int last_notice_id, int last_alarm_id) {
		// TODO
	}
}