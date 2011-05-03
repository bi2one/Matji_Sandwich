package com.matji.sandwich.http;

import android.os.AsyncTask;

import com.matji.sandwich.Requestable;

public class HttpRequestManager {
    Spinner spinner;

    public HttpRequestManager() {
	spinner = new NormalSpinner();
    }
    
    public void request(HttpRequest request, Requestable activity) {
	
    }

    private class HttpAsyncTask extends AsyncTask<Integer, Integer, Integer> {
	protected int mId = 0;

	protected Integer doInBackground(Integer... params) {
	    mId = params[0];
	    return onRequestData(mId);
	}

	protected void onCancelled() {
	    stopSpinner();
	}

	protected void onPostExecute(Integer result) {
	    stopSpinner();
	    onPostRequestData(mId);
	}
    }
}