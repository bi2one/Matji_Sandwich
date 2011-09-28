package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.AppVersion;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.VersionHttpRequest;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class IntroActivity extends BaseActivity implements Requestable {
	private String ver;
	private String update_ver;
	private HttpRequest request;
	private HttpRequestManager manager;
	private static final int a = 1;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	manager = HttpRequestManager.getInstance(this);
    	request = new VersionHttpRequest(getApplicationContext());
    	((VersionHttpRequest) request).actionAppVersion("ANDROID", ver);
    	manager.request(getMainView(), request, a, this);
    }

    @Override
    protected void init() {
        super.init();
    	ver = getResources().getString(R.string.settings_service_version_name);
        SimpleAlertDialog dialog = new SimpleAlertDialog(this, ver + update_ver);
        
        dialog.show();
        setContentView(R.layout.activity_intro);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Session session  = Session.getInstance(IntroActivity.this);
                if (session.isLogin()) session.unsyncSessionValidate();
                session.notificationValidate();
                ImageLoader.clearCache(getApplicationContext());
                startActivity(new Intent(IntroActivity.this, MainTabActivity.class));
                finish();
            }
        };

        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public int setMainViewId() {
        // TODO Auto-generated method stub
        return R.id.activity_intro;
    }

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case a:
			if (data != null && data.size() > 0) {
				AppVersion app_version = (AppVersion)data.get(0);
				Log.d("asdfasdf",app_version+"");
			} else {
				Log.d("Matji", "not exist data ...");
			}
		}
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
		
	}
}