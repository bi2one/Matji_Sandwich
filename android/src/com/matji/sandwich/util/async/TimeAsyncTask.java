package com.matji.sandwich.util.async;

import android.os.AsyncTask;
import android.util.Log;

public class TimeAsyncTask extends AsyncTask<Object, Long, Long> {
    public long startTime;
    public long currentTime;
    public long elapsedTime;
    public TimeListener listener;

    public void execute() {
	startTime = System.currentTimeMillis();
	execute(new Object());
    }

    public void setTimeListener(TimeListener listener) {
	this.listener = listener;
    }

    public long getStartTime() {
	return startTime;
    }

    public long getElapsedTime() {
	return elapsedTime;
    }

    public long getCurrentTime() {
	return currentTime;
    }

    protected void onPreExecute() {
	super.onPreExecute();
    }

    protected Long doInBackground(Object... arg0) {
	while(true) {
	    if (currentTime != System.currentTimeMillis()) {
		currentTime = System.currentTimeMillis();
		elapsedTime = currentTime - startTime;
		publishProgress(startTime, currentTime, elapsedTime);
	    }
	}
    }

    protected void onProgressUpdate(Long... values) {
	if (listener != null)
	    listener.onElapsedTime(this, values[0], values[1], values[2]);
    }

    public interface TimeListener {
	public void onElapsedTime(AsyncTask task, long startTime, long currentTime, long elapsedTime);
    }
}