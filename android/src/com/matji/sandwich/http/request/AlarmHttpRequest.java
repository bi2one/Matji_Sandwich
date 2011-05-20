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
    private MatjiDataParser parser;
    protected String action;
    protected String controller;
    
    public AlarmHttpRequest(Context context) {
    	super(context);
    	parser = new AlarmParser();
    	controller = "alarms";
    }

    public void actionList(int user_id){
    	parser = new AlarmParser();
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "list";
    	
    	getHashtable.clear();
    	getHashtable.put("user_id", user_id + "");
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	SimpleHttpResponse response = 
		(httpMethod == HttpMethod.HTTP_POST) ? 
				requestHttpResponsePost(serverDomain + controller + "/" + action , null, postHashtable)
				:requestHttpResponseGet(serverDomain + controller + "/" + action , null, getHashtable); 

	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	Log.d("Matji", "AlarmHttpRequest resultBody: " + resultBody);
	Log.d("Matji", "AlarmHttpRequest resultCode: " + resultCode);
	
	return parser.parseToMatjiDataList(resultBody);
    }
}