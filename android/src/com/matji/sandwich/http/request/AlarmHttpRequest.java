package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.AlarmParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

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