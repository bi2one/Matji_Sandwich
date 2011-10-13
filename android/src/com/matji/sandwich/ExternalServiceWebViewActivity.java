package com.matji.sandwich;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Me;
import com.matji.sandwich.exception.ExceptionFactory;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.parser.MeParser;
import com.matji.sandwich.http.request.MeHttpRequest.Service;
import com.matji.sandwich.session.Session;

public class ExternalServiceWebViewActivity extends Activity {//implements IUserData {
    private static final String TAG = "Web";
    public enum ExternalService { LOGIN, LINK }
    public static final String SERVICE = "com.matji.sandwich.ExternalServiceWebViewActivity.service";
    public static final String EXTERNAL_SERVICE = "com.matji.sandwich.ExternalServiceWebViewActivity.external_service";
    public static final String URL = "com.matji.sandwich.ExternalServiceWebViewActivity.url";
    private ExternalService externalService;
    private Service service;
    private JavaScriptInterface jsInterface;
    private WebView wv;

    private Session session;
    //    private boolean requestFlag = false;
    //private String service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initValues();
        setContentView(wv);
        configureValues();
    }

    private void configureValues() {
        externalService = (ExternalService) getIntent().getSerializableExtra(EXTERNAL_SERVICE);
        if (externalService == ExternalService.LINK) {
            service = (Service) getIntent().getSerializableExtra(SERVICE);
        }
        String targetUrl = getIntent().getStringExtra(URL);
        session = Session.getInstance(this);
        //service = getIntent().getStringExtra("service");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(jsInterface, "MatjiExternalService");

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, url);
                wv.loadUrl("javascript:window.MatjiExternalService.getOAuthResult(document.getElementById('yummystory_result_code').value);");
            }
        });
        wv.loadUrl(targetUrl);
    }

    private void initValues() {
        wv = new WebView(this);
        jsInterface = new JavaScriptInterface();

    }

    class JavaScriptInterface {
        public void getOAuthResult(String jsonString) {
            Log.d(TAG, "getOAuthResult == " + jsonString);
            if (externalService == ExternalService.LOGIN) {
                if (jsonString != null && jsonString.length() > 0){
                    try{
                        // write to local database
                        MeParser parser = new MeParser();
                        ArrayList<MatjiData> md = parser.parseToMatjiDataList(jsonString);
                        Me me = (Me)md.get(0);
                        Session.getInstance(ExternalServiceWebViewActivity.this).saveMe(me);

                        setResult(Activity.RESULT_OK);
                        finish();
                    }catch (MatjiException e){
                        e.printStackTrace();
                    }
                }
            } else if (externalService == ExternalService.LINK) { 
                JSONObject json;
                try {
                    json = new JSONObject(jsonString);
                    int code = json.getInt("code");
                    if (code == ExceptionFactory.STATUS_OK) {
                        externalServiceRefresh();
                    }else if (code == ExceptionFactory.UNKNOWN_ERROR) {
                        // TODO error message....
                    }
                    finish();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        public void externalServiceRefresh() {
            if (service == Service.TWITTER) {
                session.getCurrentUser().getExternalAccount().setLinkedTwitter(true);
            } else if (service == Service.FACEBOOK) {
                session.getCurrentUser().getExternalAccount().setLinkedFacebook(true);
            }            
        } 
    }
}