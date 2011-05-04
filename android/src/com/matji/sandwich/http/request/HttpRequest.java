package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

public abstract class HttpRequest {
    protected MatjiDataParser parser = null;
    protected Context context = null;

    public void setContext(Context context) {
	this.context = context;
    }

    public abstract ArrayList<MatjiData> request();

    public void setStringHashtable(Hashtable<String, String> hashtable) { }

    public void setObjectHashtable(Hashtable<String, Object> hashtable) { }
    
    protected SimpleHttpResponse requestHttpResponseGet(String url, Map<String, String> header, Map<String, String> param) {
	return requestHttpResponse(url, header, null, param, HttpUtility.ASYNC_METHOD_GET);
    }

    protected SimpleHttpResponse requestHttpResponsePost(String url, Map<String, String> header, Map<String, Object> param) {
	return requestHttpResponse(url, header, param, null, HttpUtility.ASYNC_METHOD_POST);
    }
    
    private SimpleHttpResponse requestHttpResponse(String url,
						     Map<String, String> header,
						     Map<String, Object> postParam, Map<String, String> getParam, int method) {
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
			
	    try {
    		Log.d("REQUEST_URL", url);
				
		if(method == HttpUtility.ASYNC_METHOD_POST) {
		    httpResponse = HttpUtility.getInstance().post(url, baseHeader, postParam);
		} else {
		    httpResponse = HttpUtility.getInstance().get(url, baseHeader, getParam);
		    Log.d("REQUEST_PARAM", getParam.toString());
		}

    		// Log.d("REQUEST_CODE", httpResponse.getHttpStatusCode() + "");
    		// Log.d("REQUEST_BODY", httpResponse.getHttpResponseBodyAsString());
    	    } catch (Exception e) {
    		e.printStackTrace();
	    }
	} else {
	    httpResponse = null;
	    // exception 발생하게 할 것.
	}
        // httpResponse가 null일때 exception이 발생하게 할 것.
	return httpResponse;
    }
}