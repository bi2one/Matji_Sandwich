package com.matji.sandwich.http;

import android.os.AsyncTask;
import android.app.Activity;
import android.util.Log;
import android.content.Context;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.widget.Spinner;
import com.matji.sandwich.widget.NormalSpinner;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Stack;

public class HttpRequestManager {
	private Context context;
	private Requestable requestable;
	private volatile static Spinner spinner;
	private HttpAsyncTask httpAsyncTask;
	private Stack<RequestTagPair> requestPool;
	private Stack<DataTagPair> dataPool;
	private MatjiException lastOccuredException = null;
	private Activity mParentActivity;
	private boolean isRunning = false;

	public HttpRequestManager(Context context, Requestable requestable) {
		this.context = context;
		this.requestable = requestable;
		requestPool = new Stack<RequestTagPair>();
		dataPool = new Stack<DataTagPair>();
	}

	// public void request(HttpRequest request, int tag) {
	// 	this.request = request;
	// 	this.lastOccuredException = null;
	// 	request.setContext(context);
	// 	httpAsyncTask = new HttpAsyncTask();
	// 	httpAsyncTask.execute(tag);
	// }

	public void request(Activity parentActivity, HttpRequest request, int tag) {
		this.mParentActivity = parentActivity;
		
		spinner = getSpinner();
		requestPool.push(new RequestTagPair(request, tag));

		this.lastOccuredException = null;
		//request.setContext(context);
		httpAsyncTask = new HttpAsyncTask();
		httpAsyncTask.execute(tag);
	}

	private Spinner getSpinner() {
		if (spinner == null){
			synchronized(NormalSpinner.class) {
				if (spinner == null) {
					spinner = new NormalSpinner(context);
				}
			}
		}
		return spinner;
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
			synchronized(Stack.class) {
				Log.d("Matji", "Yaho");
				RequestTagPair pair = requestPool.pop();
				dataPool.push(new DataTagPair(pair.getRequest().request(), pair.getTag()));
			}
		} catch(MatjiException e) {
			lastOccuredException = e;
		}
	}

	private void onPostRequestData(int tag) {
		
		if (lastOccuredException != null){
			requestable.requestExceptionCallBack(tag, lastOccuredException);
		}
		else {
			synchronized(Stack.class) {
				DataTagPair pair = dataPool.pop();
				requestable.requestCallBack(pair.getTag(), pair.getData());
			}
		}
	}

	private void startSpinner() {
		if (!requestPool.empty()) {
			isRunning = true;
			if (spinner != null) {
				Log.d("refresh", "Start spinner");
				spinner.start(mParentActivity);
			}
		}
	}

	private void stopSpinner() {
		if (requestPool.empty()) {
			isRunning = false;
			if (spinner != null) {
				Log.d("refresh", "Stop spinner");
				spinner.stop();
			}
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

	private class DataTagPair {
		private ArrayList<MatjiData> data;
		private int tag;

		public DataTagPair(ArrayList<MatjiData> data, int tag) {
			this.data = data;
			this.tag = tag;
		}

		public ArrayList<MatjiData> getData() {
			return data;
		}

		public int getTag() {
			return tag;
		}
	}
	
	private class RequestTagPair {
		private HttpRequest request;
		private int tag;

		public RequestTagPair(HttpRequest request, int tag) {
			this.request = request;
			this.tag = tag;
		}

		public HttpRequest getRequest() {
			return request;
		}

		public int getTag() {
			return tag;
		}
	}    
}