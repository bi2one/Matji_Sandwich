package com.matji.sandwich.http.request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.matji.sandwich.ExternalServiceWebViewActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.parser.MeParser;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;

public class MeHttpRequest extends HttpRequest {
    public enum Service {TWITTER, FACEBOOK};
    private static final String appId = MatjiConstants.string(R.string.client_id);
    private static final String appSecret = MatjiConstants.string(R.string.client_secret);
    private static final String redirectURI = MatjiConstants.string(R.string.server_domain) + "callback";
    // private static final String redirectURI = "http://api.matji.com/callback";
	
    public MeHttpRequest(Context context) {
	super(context);
	parser = new MeParser();
    }
	
    public void actionMe() {
    	action = "me";
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
    
    
    public void authorizeViaExternalService(Activity activity, Service service){
    	action = "authorize";
    	getHashtable.clear();
    	
    	if (service == Service.TWITTER){
	    getHashtable.put("response_type", "twitter");
    	}else if (service == Service.FACEBOOK){
	    getHashtable.put("response_type", "facebook");
    	}
    	
    	getHashtable.put("client_id", appId);
    	getHashtable.put("client_secret", appSecret);
    	getHashtable.put("redirect_uri", redirectURI);
    	
    	
    	String url = HttpUtility.getUrlStringWithQuery(serverDomain + action, getHashtable);
    	Intent intent = new Intent(activity, ExternalServiceWebViewActivity.class);
    	intent.putExtra("url", url);
    	activity.startActivityForResult(intent, BaseActivity.REQUEST_EXTERNAL_SERVICE_LOGIN);

    }
}
