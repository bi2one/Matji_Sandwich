package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.FoodParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class FoodHttpRequest extends HttpRequest {
    private Hashtable<String, String> hashtable;
    private int page;
    private int id;
    private MatjiDataParser parser;

    public FoodHttpRequest() {
	parser = new FoodParser();
	hashtable = new Hashtable<String, String>();
	initParam();
    }

    private void setPage(int page){
    	this.page = page;
    }
    
    public void initParam() {
    	page = 1;
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	hashtable.clear();
	hashtable.put("page", page + "");

	SimpleHttpResponse response = requestHttpResponseGet("https://ygmaster.net/foods/list.json?store_id=12296", null, hashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	return parser.getData(resultBody);
    }
}