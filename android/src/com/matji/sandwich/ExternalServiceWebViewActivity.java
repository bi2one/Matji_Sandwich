package com.matji.sandwich;
//


import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Me;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.parser.MeParser;
import com.matji.sandwich.session.Session;

import android.os.Bundle;
import android.app.Activity;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;


 

public class ExternalServiceWebViewActivity extends Activity {//implements IUserData {
    private JavaScriptInterface jsInterface;
    private WebView wv;
    private Context context;
    private boolean requestFlag = false;
    //private String service;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initValues();
        setContentView(wv);
        configureValues();
    }
	
    private void configureValues() {
        String targetUrl = getIntent().getStringExtra("url");
        //service = getIntent().getStringExtra("service"); 

        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(jsInterface, "MatjiExternalService");
        
        wv.setWebViewClient(new WebViewClient() {
        	@Override
		    public void onPageFinished(WebView view, String url) {
		    Log.d("Web", url);
		    wv.loadUrl("javascript:window.MatjiExternalService.getOAuthResult(document.getElementById('oauth_response').value);");
        	}
	    });
        wv.loadUrl(targetUrl);
    }
	
    private void initValues() {
	wv = new WebView(this);
	jsInterface = new JavaScriptInterface();
	context = this;

    }
	
    class JavaScriptInterface {
	public void getOAuthResult(String jsonString) {
	    Log.d("Web", "getOAuthResult == " + jsonString);
	    if (jsonString != null && jsonString.length() > 0){
		try{
		    // write to local database
		    MeParser parser = new MeParser(context);
		    ArrayList<MatjiData> md = parser.parseToMatjiDataList(jsonString);
		    Me me = (Me)md.get(0);
		    Session.getInstance(context).saveMe(me);
					
		    setResult(Activity.RESULT_OK);
		    finish();
		}catch (MatjiException e){
		    e.printStackTrace();
		}
	    }
			
	}
		
    }
}
