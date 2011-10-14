package com.matji.sandwich;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.title.HomeTitle;

public class SimpleWebviewActivity extends BaseActivity {

    public static final String URL = "com.matji.sandwich.SimpleWebviewActivity.url";
    public static final String TITLE = "com.matji.sandwich.SimpleWebviewActivity.title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_simple_webview);
        HomeTitle title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(getIntent().getStringExtra(TITLE));
        WebView wb = (WebView) findViewById(R.id.simple_webview);
        wb.setWebViewClient(new SimpleWebviewClient());
        wb.loadUrl(getIntent().getStringExtra(URL));
        wb.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public int setMainViewId() {
        return R.id.activity_simple_webview;
    }
    
    class SimpleWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }   
    }
}
