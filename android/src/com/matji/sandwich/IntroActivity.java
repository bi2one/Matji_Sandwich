package com.matji.sandwich;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.AsyncTask;
import android.util.Log;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.AppVersion;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.async.TimeAsyncTask;
import com.matji.sandwich.util.async.SimpleAsyncTask;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.VersionHttpRequest;
import com.matji.sandwich.util.async.Threadable;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class IntroActivity extends BaseActivity implements Requestable,
							   TimeAsyncTask.TimeListener,
							   SimpleAsyncTask.ProgressListener {
    private static final long LOADING_MIN_TIME = 1000;
    private static final long DIALOG_MIN_TIME = 1500;
    private ProgressDialog dialog;
    private TimeAsyncTask timeAsyncTask;
    private SimpleAsyncTask simpleAsyncTask;
    private long lastElapsedTime;
    private String update_ver;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
	MatjiConstants.setContext(getApplicationContext());
        setContentView(R.layout.activity_intro);
	dialog = new ProgressDialog(this);
	dialog.setMessage(MatjiConstants.string(R.string.dialog_intro_loading));
	dialog.setIndeterminate(true);
	dialog.setCancelable(false);
	
	timeAsyncTask = new TimeAsyncTask();
	timeAsyncTask.setTimeListener(this);
	
	simpleAsyncTask = new SimpleAsyncTask(new SessionRunnable());
	simpleAsyncTask.setProgressListener(this);
	
	String ver = MatjiConstants.string(R.string.settings_service_version_name);
	HttpRequestManager manager = HttpRequestManager.getInstance(this);
	VersionHttpRequest request = new VersionHttpRequest(this);
	request.actionAppVersion("ANDROID", ver);
	manager.request(getMainView(), request, 0, this);
	}

    public void onElapsedTime(AsyncTask task, long startTime, long currentTime, long elapsedTime) {
	lastElapsedTime = elapsedTime;
	if (elapsedTime > DIALOG_MIN_TIME) {
	    dialog.show();
	}
    }

    public void onStart(Threadable task) { }
    public void onFinish(Threadable task) {
	timeAsyncTask.setTimeListener(null);
	if (lastElapsedTime < LOADING_MIN_TIME) {
	    try {
		Thread.sleep(LOADING_MIN_TIME - lastElapsedTime);
	    } catch(InterruptedException e) {
		e.printStackTrace();
	    }
	}
	dialog.dismiss();
	finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
	timeAsyncTask.execute();
	simpleAsyncTask.execute();
    }

    @Override
    public int setMainViewId() {
        return R.id.activity_intro;
    }

    public class SessionRunnable implements Runnable {
	public void run() {
	    Session session = Session.getInstance(IntroActivity.this);
	    if (session.isLogin()) session.unsyncSessionValidate();
	    session.notificationValidate();
	    ImageLoader.clearCache(getApplicationContext());
	    startActivity(new Intent(IntroActivity.this, MainTabActivity.class));
	}
    }

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		// TODO Auto-generated method stub
		
	}
}