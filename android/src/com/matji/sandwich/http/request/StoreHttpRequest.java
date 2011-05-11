package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.StoreParser;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.HttpConnectMatjiException;

import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Log;

public class StoreHttpRequest extends HttpRequest {
    private Hashtable<String, String> getHashtable;
    private Hashtable<String, Object> postHashtable;
    private MatjiDataParser parser;
    private float lat_sw,lat_ne,lng_sw,lng_ne;
    private boolean isPost;
    private int store_id;
    private String type;

    public StoreHttpRequest() {
	parser = new StoreParser();
	getHashtable = new Hashtable<String, String>();
	initParam();
    }

    public void count(float lat_sw, float lat_ne, float lng_sw, float lng_ne, String type){
    	getHashtable.clear();
    	this.isPost = false;
    	this.lat_sw = lat_sw;
    	this.lat_ne = lat_ne;
    	this.lng_sw = lng_sw;
    	this.lng_ne = lng_ne;
    	getHashtable.put("lat_ne", lat_ne + "");
    	getHashtable.put("lat_sw", lat_sw + "");
    	getHashtable.put("lng_ne", lng_ne + "");
    	getHashtable.put("lng_sw", lng_sw + "");
    }

    public void show(int store_id){
    	getHashtable.clear();
    	this.store_id = store_id;
    }
    
    
    public void initParam() {
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	getHashtable.clear();

	SimpleHttpResponse response = requestHttpResponseGet("http://mapi.ygmaster.net/my_stores", null, getHashtable);
	
	String resultBody = response.getHttpResponseBodyAsString();
	String resultCode = response.getHttpStatusCode() + "";
	
	return parser.getData(resultBody);
    }
}