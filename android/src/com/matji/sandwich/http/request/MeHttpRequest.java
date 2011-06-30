package com.matji.sandwich.http.request;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.parser.MeParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.session.Session;

public class MeHttpRequest extends HttpRequest {
	private static final String appId = "180d68e304";
	private static final String appSecret = "ec04ba2a0b8ed62f3a99a896aed2aa";
	private static final String redirectURI = "http://api.matji.com/callback.json"; 
	
	private String action;
    private MatjiDataParser parser;
	
	public MeHttpRequest(Context context){
		super(context);
		parser = new MeParser(context);
	}
	
	
    public ArrayList<MatjiData> request() throws MatjiException {
    	SimpleHttpResponse response = (httpMethod == HttpMethod.HTTP_POST) ? 
    			requestHttpResponsePost(serverDomain + action , null, postHashtable)
    			:requestHttpResponseGet(serverDomain + action , null, getHashtable);

    	String resultBody = response.getHttpResponseBodyAsString();
    	String resultCode = response.getHttpStatusCode() + "";

    	Log.d("Matji", "MeHttpRequest resultBody: " + resultBody);
    	Log.d("Matji", "MeHttpRequest resultCode: " + resultCode);

    	return parser.parseToMatjiDataList(resultBody);
    }

    
    public void actionMe() {
    	action = "me";
    	Session session = Session.getInstance(context);
    	
    	if (session != null && session.isLogin())
    		getHashtable.put("access_token", "" + session.getToken());   
    }
    
    
    
    public void actionAuthorize(String userid, String password) {
    	action = "authorize";   
   	
    	getHashtable.clear();
    	getHashtable.put("response_type", "password");
    	getHashtable.put("client_id", appId);
    	getHashtable.put("client_secret", appSecret);
    	getHashtable.put("userid", userid);
    	getHashtable.put("password", password);
    	getHashtable.put("redirect_uri", redirectURI);
    	
    }
}
