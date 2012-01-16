package com.matji.sandwich.http.request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.matji.sandwich.ExternalServiceWebViewActivity;
import com.matji.sandwich.ExternalServiceWebViewActivity.ExternalService;
import com.matji.sandwich.R;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.parser.MeParser;
import com.matji.sandwich.http.util.HttpUtility;
import com.matji.sandwich.util.MatjiConstants;

public class MeHttpRequest extends HttpRequest {
    public enum Service {TWITTER, FACEBOOK};
    private static final String appId = MatjiConstants.string(R.string.client_id);
    private static final String appSecret = MatjiConstants.string(R.string.client_secret);
    private static final String redirectURI = MatjiConstants.string(R.string.server_domain) + "callback";

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

    /* 
     * 트위터, 페이스북 아이디로 로그인 
     * 로그인 후에 야미스토리 유저 생성
     */
    public void authorizeViaExternalService(Context context, Service service){
        action = "authorize";
        getHashtable.clear();

        if (service == Service.TWITTER){
            getHashtable.put("response_type", "twitter");
        } else if (service == Service.FACEBOOK){
            getHashtable.put("response_type", "facebook");
        }

        getHashtable.put("client_id", appId);
        getHashtable.put("client_secret", appSecret);
        getHashtable.put("redirect_uri", redirectURI);

        String url = HttpUtility.getUrlStringWithQuery(serverDomain + action, getHashtable);
        Intent intent = new Intent(context, ExternalServiceWebViewActivity.class);
        intent.putExtra(ExternalServiceWebViewActivity.EXTERNAL_SERVICE, ExternalService.LOGIN);
        intent.putExtra(ExternalServiceWebViewActivity.URL, url);
        ((Activity) context).startActivityForResult(intent, BaseActivity.EXTERNAL_SERVICE_LOGIN_REQUEST);

    }

    /* 트위터, 페이스북 연동 */
    public void newExternalAccount(Activity activity, Service service){
        controller = "external_accounts";
        action = "new";
        String format = "json";
        
        getHashtable.clear();

        if (session != null && session.isLogin()) {
            if (service == Service.TWITTER){
                getHashtable.put("service", "twitter");
            }else if (service == Service.FACEBOOK){
                getHashtable.put("service", "facebook");
            }        
            getHashtable.put("access_token", "" + session.getToken());
        }

        String url = HttpUtility.getUrlStringWithQuery(serverDomain + controller + "/" + action + "." + format, getHashtable);
        Intent intent = new Intent(activity, ExternalServiceWebViewActivity.class);
        intent.putExtra(ExternalServiceWebViewActivity.EXTERNAL_SERVICE, ExternalService.LINK);
        intent.putExtra(ExternalServiceWebViewActivity.URL, url);
        intent.putExtra(ExternalServiceWebViewActivity.SERVICE, service);
        activity.startActivityForResult(intent, BaseActivity.EXTERNAL_SERVICE_LINK_REQUEST);
    }    
    
    /* 트위터, 페이스북 연동 해제 */
    public void deleteExternalAccount(Activity activity, Service service){
        controller = "external_accounts";
        action = "delete";
        
        getHashtable.clear();

        if (session != null && session.isLogin()) {
            if (service == Service.TWITTER){
                getHashtable.put("service", "twitter");
            }else if (service == Service.FACEBOOK){
                getHashtable.put("service", "facebook");
            }        
            getHashtable.put("access_token", "" + session.getToken());
        }
    }
}