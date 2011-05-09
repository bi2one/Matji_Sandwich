package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.PostParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class PostHttpRequest extends HttpRequest {
    private Hashtable<String, String> hashtable;
    private int id;
    private int page;
    private MatjiDataParser parser;
    
    public PostHttpRequest() {
	parser = new PostParser();
	hashtable = new Hashtable<String, String>();
    }

    private void setPage(int page){
    	this.page = page;
    }
    public void initParam() {
    	page = 0;
    	id = 1;
	}

    public void setStringHashtable(Hashtable<String, String> hashtable) {
	this.hashtable = hashtable;
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	SimpleHttpResponse response = requestHttpResponseGet("http://ygmaster.net/posts", null, hashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	return parser.getData(resultBody);
    }

	}