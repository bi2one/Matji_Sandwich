package com.matji.sandwich.http;

import android.os.AsyncTask;
import android.app.Activity;
import android.util.Log;
import android.content.Context;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.widget.Spinner;
import com.matji.sandwich.widget.NormalSpinner;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;

public class HttpRequestManager {
    private Context context;
    private Requestable requestable;
    private Spinner spinner;
    private HttpAsyncTask httpAsyncTask;
    private ArrayList<MatjiData> data;
    private HttpRequest request;
    private MatjiException lastOccuredException = null;
    private boolean isRunning = false;

    public HttpRequestManager(Context context, Requestable requestable) {
	this.context = context;
	this.requestable = requestable;
    }
    
    public void request(HttpRequest request, int tag) {
	this.request = request;
	this.lastOccuredException = null;
	request.setContext(context);
	httpAsyncTask = new HttpAsyncTask();
	httpAsyncTask.execute(tag);
    }

    public void request(Activity parentActivity, HttpRequest request, int tag) {
	spinner = new NormalSpinner(context, parentActivity);
	this.request = request;
	this.lastOccuredException = null;
	request.setContext(context);
	httpAsyncTask = new HttpAsyncTask();
	httpAsyncTask.execute(tag);
    }

    public boolean isRunning() {
	return isRunning;
    }
    
    public void cancel() {
	if (httpAsyncTask != null) {
	    if (!httpAsyncTask.isCancelled()) {
		httpAsyncTask.cancel(true);
	    }
	    httpAsyncTask = null;
	}
    }

    private void onPreRequestData(int tag) {
	// Log.d("Request_Test", "pre request data!!");
    }

    private void onRequestData(int tag) {
	// Log.d("Request_Test", "on request data!!");
	try {
	    data = request.request();
	} catch(MatjiException e) {
	    lastOccuredException = e;
	}
    }

    private void onPostRequestData(int tag) {
	if (lastOccuredException != null)
	    requestable.requestExceptionCallBack(tag, lastOccuredException);
	else
	    requestable.requestCallBack(tag, data);
    }

    private void startSpinner() {
	isRunning = true;
	if (spinner != null) {
	    spinner.start();
	}
    }

    private void stopSpinner() {
	isRunning = false;
	if (spinner != null) {
	    spinner.stop();
	}
    }

    private class HttpAsyncTask extends AsyncTask<Integer, Integer, Integer> {
	protected int mId = 0;

	protected Integer doInBackground(Integer... params) {
	    mId = params[0];
	    onRequestData(mId);
	    return mId;
	}

	protected void onCancelled() {
	    stopSpinner();
	}

	protected void onPostExecute(Integer result) {
	    stopSpinner();
	    onPostRequestData(mId);
	}

	protected void onPreExecute() {
	    startSpinner();
	    onPreRequestData(mId);
	}

	protected void onProgressUpdate(Integer... values) { }
    }
}