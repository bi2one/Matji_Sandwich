package com.matji.sandwich.http.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.HttpConnectMatjiException;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.parser.MatjiParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.listener.ProgressListener;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;

enum HttpMethod { HTTP_POST, HTTP_GET, HTTP_GET_VIA_WEB_BROWSER }

public abstract class HttpRequest implements ProgressRequestCommand {
    protected Context context = null;
    // protected String serverDomain = "http://api.matji.com/v2/";
    protected String serverDomain = MatjiConstants.string(R.string.server_domain);

    protected Hashtable<String, Object> postHashtable;
    protected Hashtable<String, String> getHashtable;
    protected HttpMethod httpMethod;
    protected MatjiParser parser;

    protected String action;
    protected String controller;

    private ProgressListener progressListener;
    private int progressTag;

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

	Log.d("request", getClass() + " resultBody:" + resultBody);
	Log.d("request", getClass() + " resultCode: " + resultCode);

	ArrayList<MatjiData> result = parser.parseToMatjiDataList(resultBody);
	if (progressListener != null) {
	    progressListener.onUnitWritten(progressTag, getRequestCount(), 1);
	}
	return result;
    }

    protected SimpleHttpResponse requestHttpResponseGet(Map<String, String> header, Map<String, String> param)
	throws HttpConnectMatjiException {
	return requestHttpResponse(getUrl(), header, null, param, HttpUtility.ASYNC_METHOD_GET);
    }

    protected SimpleHttpResponse requestHttpResponsePost(Map<String, String> header, Map<String, Object> param)
	throws HttpConnectMatjiException {
	return requestHttpResponse(getUrl(), header, param, null, HttpUtility.ASYNC_METHOD_POST);
    }

    public void setProgressListener(int progressTag, ProgressListener listener) {
	HttpUtility.getInstance().setProgressListener(progressTag, listener);
	this.progressTag = progressTag;
	this.progressListener = listener;
    }

    public int getRequestCount() {
	return 1;
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

	    baseHeader.put("Agent", "MATJI_ANDROID");
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
		httpResponse = utility.post(url, baseHeader, postParam, this);
	    } else {
		httpResponse = HttpUtility.getInstance().get(url, baseHeader, getParam, this);
	    }
	} else {
	    httpResponse = null;
	}

	if (httpResponse == null) {
	    throw new HttpConnectMatjiException();
	} else
	    return httpResponse;
    }

    
    public void cancel() {
	if (httpMethod == HttpMethod.HTTP_POST || httpMethod == HttpMethod.HTTP_GET) {
	    HttpUtility.getInstance().disconnectConnection(this);
	}
    }
    
    public String getUrl() {
	if (action == null && controller == null) {
	    return serverDomain;
	} else if (controller == null || controller.equals("")) {
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

    public boolean equals(HttpRequest request) {
	if (httpMethod != request.getHttpMethod()) {
	    return false;
	}
	
	if (httpMethod == HttpMethod.HTTP_POST ||
	    request.getHttpMethod() == HttpMethod.HTTP_POST) {
	    return false;
	}
			
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
