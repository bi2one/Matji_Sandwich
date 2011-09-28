package com.matji.sandwich.util.async;

import android.os.AsyncTask;
import android.util.Log;

public class SimpleAsyncTask extends AsyncTask<Object, Object, Object> {
    public long startTime;
    public long currentTime;
    public long elapsedTime;
    public Runnable runnable;
    public ProgressListener listener;

    public SimpleAsyncTask(Runnable runnable) {
	this.runnable = runnable;
    }

    public void setProgressListener(ProgressListener listener) {
	this.listener = listener;
    }

    protected void onPreExecute() {
	if (listener != null)
	    listener.onStart(this);
    }

    protected void onCancelled() {
	if (listener != null)
	    listener.onCancel(this);
    }

    protected void onPostExecute(Object result) {
	if (listener != null)
	    listener.onFinish(this);
    }
    
    public void execute() {
	execute(new Object());
    }

    protected Object doInBackground(Object... arg0) {
	runnable.run();
	return null;
    }

    public interface ProgressListener {
	public void onStart(AsyncTask task);
	public void onFinish(AsyncTask task);
	public void onCancel(AsyncTask task);
    }
}