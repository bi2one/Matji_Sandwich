package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.AlarmParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class AlarmHttpRequest extends HttpRequest {
    private Hashtable<String, String> hashtable;
    private String action ;
    private MatjiDataParser parser;

    public AlarmHttpRequest() {
	parser = new AlarmParser();
	hashtable = new Hashtable<String, String>();
	initParam();
    }

    public void setAction(String action){
    	this.action = action;
    }

    public void initParam() {
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	hashtable.clear();

	SimpleHttpResponse response = requestHttpResponseGet(serverDomain +"alarms/" + action + ".json", null, hashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	return parser.getData(resultBody);
    }
}