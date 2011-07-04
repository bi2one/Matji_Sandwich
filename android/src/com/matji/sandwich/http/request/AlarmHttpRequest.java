package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.AlarmParser;

import android.content.Context;

public class AlarmHttpRequest extends HttpRequest {
    public AlarmHttpRequest(Context context) {
    	super(context);
    	parser = new AlarmParser(context);
    	controller = "alarms";
    }

    public void actionList(int page, int limit) {
    	parser = new AlarmParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "list";
    	
    	getHashtable.clear();
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + "");
    }
}