package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.UserMileageParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class UserMileageHttpRequest extends HttpRequest {
    private Hashtable<String, String> hashtable;
    private int page;
    private int id;
    private MatjiDataParser parser;

    public UserMileageHttpRequest() {
	parser = new UserMileageParser();
	hashtable = new Hashtable<String, String>();
	initParam();
    }

    private void setPage(int page){
    	this.page = page;
    }
    
    public void initParam() {
    	page = 0;
    	id = 1;
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	hashtable.clear();
	hashtable.put("page", page + "");
	hashtable.put("id", id + "");

	SimpleHttpResponse response = requestHttpResponseGet("http://mapi.ygmaster.net/my_stores", null, hashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	return parser.getData(resultBody);
    }
}