package com.matji.sandwich.http.request;

import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.listener.FileUploadProgressListener;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.HttpConnectMatjiException;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.util.Log;

enum HttpMethod { HTTP_POST, HTTP_GET, HTTP_GET_VIA_WEB_BROWSER}

public abstract class HttpRequest {
    private int tag;
    private FileUploadProgressListener progressListener;
    protected Context context = null;
    protected String serverDomain = "http://api.matji.com:3000/v2/";
    
    protected Hashtable<String, Object> postHashtable;
    protected Hashtable<String, String> getHashtable;
    protected HttpMethod httpMethod;
    protected MatjiDataParser parser;

    protected String action;
    protected String controller;
	
    @SuppressWarnings("unused")
	private HttpRequest(){}
	
    public HttpRequest(Context context) {
	this.context = context;
	postHashtable = new Hashtable<String, Object>();
	getHashtable = new Hashtable<String, String>();
    }

    public ArrayList<MatjiData> request() throws MatjiException {
    	SimpleHttpResponse response = (httpMethod == HttpMethod.HTTP_POST) ?
	    requestHttpResponsePost(null, postHashtable):requestHttpResponseGet(null, getHashtable);

    	String resultBody = response.getHttpResponseBodyAsString();
    	String resultCode = response.getHttpStatusCode() + "";

    	Log.d("Matji", "StoreHttpRequest resultBody: " + resultBody);
	Log.d("Matji", "StoreHttpRequest resultCode: " + resultCode);

    	return parser.parseToMatjiDataList(resultBody);
    }

    protected SimpleHttpResponse requestHttpResponseGet(Map<String, String> header, Map<String, String> param)
	throws HttpConnectMatjiException {
	return requestHttpResponse(getUrl(), header, null, param, HttpUtility.ASYNC_METHOD_GET);
    }

    protected SimpleHttpResponse requestHttpResponsePost(Map<String, String> header, Map<String, Object> param)
	throws HttpConnectMatjiException {
	return requestHttpResponse(getUrl(), header, param, null, HttpUtility.ASYNC_METHOD_POST);
    }

    public void setFileUploadProgressListener(FileUploadProgressListener listener) {
	progressListener = listener;
    }
    
    private SimpleHttpResponse requestHttpResponse(String url,
						   Map<String, String> header,
						   Map<String, Object> postMap,
						   Map<String, String> getMap,
						   int method) throws HttpConnectMatjiException {
	SimpleHttpResponse httpResponse = null;
	ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo netInfo_mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	NetworkInfo netInfo_wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

	Map<String, String> getParam = (getMap == null)?null:(new Hashtable(getMap));
	Map<String, Object> postParam = (postMap == null)?null:(new Hashtable(postMap));
		
	if((netInfo_mobile.getState() == NetworkInfo.State.CONNECTED) ||
	   (netInfo_wifi.getState() == NetworkInfo.State.CONNECTED)) {
	    Map<String,String> baseHeader = new HashMap<String,String>();

	    baseHeader.put("Agent", "MAJTI_ANDROID");
	    baseHeader.put("Content-Type", "text/xml");
	
	    if(header != null) {
		baseHeader.putAll(header);
	    }

	    Session session = Session.getInstance(context);
	    if (postParam != null) {
	    	if (session != null && session.isLogin())
		    postParam.put("access_token", "" + session.getToken());   
	    	postParam.put("format", "json");
	    }else if (getParam != null) {
	    	if (session != null && session.isLogin())
		    getParam.put("access_token", "" + session.getToken());
	    	getParam.put("format", "json");
	    }
	    
	    
	    if(method == HttpUtility.ASYNC_METHOD_POST) {
		HttpUtility utility = HttpUtility.getInstance();
		utility.setFileUploadProgressListener(progressListener);
		httpResponse = utility.post(url, baseHeader, postParam, tag);
		utility.removeFileUploadProgressListener();
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

    public void setTag(int tag) {
	this.tag = tag;
    }

    public int getTag() {
	return tag;
    }

    public String getUrl() {
	if (controller == null || controller.equals("")) {
	    return serverDomain + action;
	} else {
	    return serverDomain + controller + "/" + action;
	}
    }

    public HttpMethod getHttpMethod() {
	return httpMethod;
    }

    public Hashtable<String, String> getGetHashtable() {
	return getHashtable;
    }

    public boolean isEqual(HttpRequest request) {
	if (httpMethod != request.getHttpMethod())
	    return false;

	if (getUrl() == null || request.getUrl() == null) {
	    return false;
	} else if (!getUrl().equals(request.getUrl())) {
	    return false;
	} else {
	    if (httpMethod == HttpMethod.HTTP_GET) {
		String currentUrl = HttpUtility.getUrlStringWithQuery(getUrl(), getGetHashtable());
		String requestUrl = HttpUtility.getUrlStringWithQuery(request.getUrl(), request.getGetHashtable());
		return currentUrl.equals(requestUrl);
	    } else {
		return true;
	    }
	}
    }
}

// http://api.matji.com:3000/v2/stores/nearby_list?limit=60&lat_ne=37.32259&lng_sw=126.8279&order=like_count%20DESC%2Creg_user_id%20ASC&page=1&lat_sw=37.317488&lng_ne=126.833048&format=json&include=attach_file%2Cuser%2Ctags%2Cstore_foods%2Cfoods
// http://api.matji.com:3000/v2/stores/nearby_list?limit=60&lat_ne=37.32259&lng_sw=126.8279&order=like_count%20DESC%2Creg_user_id%20ASC&page=1&lat_sw=37.317488&lng_ne=126.833048&include=attach_file%2Cuser%2Ctags%2Cstore_foods%2Cfoods
