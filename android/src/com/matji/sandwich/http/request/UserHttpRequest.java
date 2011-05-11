package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.UserParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class UserHttpRequest extends HttpRequest {
    private Hashtable<String, String> hashtable;
    private MatjiDataParser parser;

    public UserHttpRequest() {
	parser = new UserParser();
	hashtable = new Hashtable<String, String>();
	initParam();
    }

    private void setPage(int page){
    }
    
    public void initParam() {

    }

    public ArrayList<MatjiData> request() throws MatjiException {
	hashtable.clear();


	SimpleHttpResponse response = requestHttpResponseGet("http://mapi.ygmaster.net/my_stores", null, hashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	return parser.getData(resultBody);
    }
}