package com.matji.sandwich.http.request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.matji.sandwich.ExternalServiceWebViewActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.parser.MeParser;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;



public class MeHttpRequest extends HttpRequest {
	public enum Service {TWITTER, FACEBOOK};
	private static final String appId = "7b679b39a5";
	private static final String appSecret = "cc2673f5efd4db1fcd318f4d6562b8";
	private static final String redirectURI = MatjiConstants.string(R.string.server_domain) + "callback";
	// private static final String appId = "d52ec64efe";
	// private static final String appSecret = "3e9f2acc6f5d6b8b0312c8321e1de4";
	// private static final String redirectURI = "http://api.matji.com/callback";
	
	public MeHttpRequest(Context context){
		super(context);
		parser = new MeParser(context);
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
