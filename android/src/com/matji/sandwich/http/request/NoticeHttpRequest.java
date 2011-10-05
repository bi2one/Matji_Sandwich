package com.matji.sandwich.http.request;

import android.content.Context;

import com.matji.sandwich.http.parser.BadgeParser;
import com.matji.sandwich.http.parser.NoticeParser;
import com.matji.sandwich.util.MatjiConstants;

public class NoticeHttpRequest extends HttpRequest {
	public NoticeHttpRequest(Context context) {
		super(context);
		controller = "notices";
	}

	public void actionShow(int notice_id) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "show";
        parser = new NoticeParser(context);
		
		getHashtable.clear();
		getHashtable.put("notice_id", notice_id + "");
	}
	
	public void actionList(int page, int limit) {
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";
        parser = new NoticeParser(context);

		getHashtable.clear();
		getHashtable.put("page", page+"");
		getHashtable.put("limit", limit+"");
		getHashtable.put("target", MatjiConstants.target());
		getHashtable.put("language", MatjiConstants.language());
	}
	
	public void actionBadge(int last_notice_id) {
	    httpMethod = HttpMethod.HTTP_GET;
	    action = "badge";
	    parser = new BadgeParser(context);

	    getHashtable.clear();
	    getHashtable.put("last_notice_id", last_notice_id+"");
	    getHashtable.put("target", MatjiConstants.target());
	    getHashtable.put("language", MatjiConstants.language());
	}
}