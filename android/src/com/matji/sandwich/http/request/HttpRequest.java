package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.exception.HttpConnectMatjiException;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.data.MatjiData;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

public abstract class HttpRequest {
    protected Context context = null;
    protected String serverDomain = "http://api.matji.com/";
//    protected String serverDomain = "http://14.63.213.202/"; 
	protected String access_token = "60e515f4d7d563b870ccdc702bfcdf5df83abb5d"; //임시
    public void setContext(Context context) {
	this.context = context;
    }

    public abstract ArrayList<MatjiData> request() throws MatjiException;
    //public void setStringHashtable(Hashtable<String, String> hashtable) { }

    //public void setObjectHashtable(Hashtable<String, Object> hashtable) { }
    
    protected SimpleHttpResponse requestHttpResponseGet(String url, Map<String, String> header, Map<String, String> param)
    throws HttpConnectMatjiException {
	return requestHttpResponse(url, header, null, param, HttpUtility.ASYNC_METHOD_GET);
    }

    protected SimpleHttpResponse requestHttpResponsePost(String url, Map<String, String> header, Map<String, Object> param)
    throws HttpConnectMatjiException {
	return requestHttpResponse(url, header, param, null, HttpUtility.ASYNC_METHOD_POST);
    }
    
    private SimpleHttpResponse requestHttpResponse(String url,
						   Map<String, String> header,
						   Map<String, Object> postParam,
						   Map<String, String> getParam,
						   int method) throws HttpConnectMatjiException {
	SimpleHttpResponse httpResponse = null;
	// 네트웍 정보 얻기
	ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo netInfo_mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	NetworkInfo netInfo_wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
	// 네트웍 접속 여부 판단
	if((netInfo_mobile.getState() == NetworkInfo.State.CONNECTED) ||
	   (netInfo_wifi.getState() == NetworkInfo.State.CONNECTED)) {
	    Map<String,String> baseHeader = new HashMap<String,String>();

	    baseHeader.put("Agent", "MAJTI_ANDROID");
	    baseHeader.put("Content-Type", "text/xml");
	
	    if(header != null) {
		baseHeader.putAll(header);
	    }

	    
	    // 전역 파라미터 설정 
	    if (postParam != null) {
	    	postParam.put("access_token", access_token); // 토큰  
	    	postParam.put("format", "json"); // 응답 포맷 
	    }
	    if (getParam != null) {
	    	getParam.put("access_token", access_token);
	    	getParam.put("format", "json");
	    }
	    
	    
	    if(method == HttpUtility.ASYNC_METHOD_POST) {
		httpResponse = HttpUtility.getInstance().post(url, baseHeader, postParam);
	    } else {
		httpResponse = HttpUtility.getInstance().get(url, baseHeader, getParam);
	    }
	} else {
	    httpResponse = null;
	}

	if (httpResponse == null)
	    throw new HttpConnectMatjiException();
	else
	    return httpResponse;
    }
}