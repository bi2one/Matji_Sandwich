package com.matji.sandwich;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.AsyncTask;
import android.util.Log;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.async.TimeAsyncTask;
import com.matji.sandwich.util.async.SimpleAsyncTask;
import com.matji.sandwich.util.async.Threadable;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class IntroActivity extends BaseActivity implements TimeAsyncTask.TimeListener,
							   SimpleAsyncTask.ProgressListener {
    private static final long LOADING_MIN_TIME = 100;
    private static final long DIALOG_MIN_TIME = 500;
    private ProgressDialog dialog;
    private TimeAsyncTask timeAsyncTask;
    private SimpleAsyncTask simpleAsyncTask;
    private long lastElapsedTime;
    
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
}