package com.matji.sandwich.http.request;

import java.util.ArrayList;
import java.util.Hashtable;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.parser.AccessGrantParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;

public class AccessGrantHttpRequest  extends HttpRequest {
    private Hashtable<String, String> hashtable;
    
    public AccessGrantHttpRequest() {
	parser = new AccessGrantParser();
	hashtable = new Hashtable<String, String>();
    }

    public void setStringHashtable(Hashtable<String, String> hashtable) {
	this.hashtable = hashtable;
    }

    public ArrayList<MatjiData> request() throws MatjiException {
   	SimpleHttpResponse response = requestHttpResponseGet("http://mapi.ygmaster.net/my_stores", null, hashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	return parser.getData(resultBody);
    }
}