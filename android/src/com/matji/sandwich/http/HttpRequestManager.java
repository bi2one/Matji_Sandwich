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
import java.util.HashMap;
import java.util.Stack;
import java.util.Iterator;
import java.util.Map;

public class HttpRequestManager {
    private static volatile HttpRequestManager manager;
    private Context context;
    private Activity mParentActivity;
    private ActivityAsyncPool asyncPool;

    private HttpRequestManager(Context context) {
	this.context = context;
	asyncPool = new ActivityAsyncPool();
    }

    public static HttpRequestManager getInstance(Context context) {
	if (manager != null && manager.context != context)
	    manager.context = context;

	if (manager == null) {
	    synchronized(HttpRequestManager.class) {
		if (manager == null) {
		    manager = new HttpRequestManager(context);
		}
	    }
	}

	return manager;
    }

    public void request(Activity parentActivity, HttpRequest request, int tag, Requestable requestable) {
	request.setTag(tag);
	asyncPool.putAndRunRequest(parentActivity, request, requestable);
    }

    public boolean isRunning(Activity parentActivity) {
	return asyncPool.isRunning(parentActivity);
    }

    public boolean isRunning() {
	return asyncPool.isRunning();
    }

    public void cancelTask() {
	asyncPool.cancelTask();
    }

    public void cancelTask(Activity parentActivity) {
	asyncPool.cancelTask(parentActivity);
    }
    

    private class HttpAsyncTask extends AsyncTask<HttpRequest, Integer, ArrayList<MatjiData>> {
	protected int tag = 0;
	protected HttpRequest request;
	private Activity parentActivity;
	private MatjiException occuredException;
	private Requestable requestable;

	    public HttpAsyncTask(Activity activity, HttpRequest request, Requestable requestable) {
	    parentActivity = activity;
	    this.request = request;
	    this.requestable = requestable;
	}

	public void execute() {
	    execute(request);
	}

	public HttpRequest getRequest() {
	    return request;
	}

	protected ArrayList<MatjiData> doInBackground(HttpRequest... params) {
	    ArrayList<MatjiData> result;
	    tag = request.getTag();
	    Log.d("refresh", "doin " + params.length);

	    try {
		result = request.request();
	    } catch(MatjiException e) {
		occuredException = e;
		return null;
	    }
	    
	    Log.d("refresh", "doin end");
	    return result;
	}

	protected void onCancelled() {
	    asyncPool.requestStopSpinner(parentActivity);
	}

	protected void onPostExecute(ArrayList<MatjiData> result) {
	    if (result == null) {
		requestable.requestExceptionCallBack(tag, occuredException);
	    } else {
		requestable.requestCallBack(tag, result);
	    }
	    
	    asyncPool.requestStopSpinner(parentActivity);
	    Log.d("refresh", "onPostExecute");
	    Log.d("refresh", "onPostExecute end");
	}

	protected void onPreExecute() {
	    asyncPool.requestStartSpinner(parentActivity);
	}

	protected void onProgressUpdate(Integer... values) { }
    }

    private class ActivityAsyncPool {
	private HashMap<Activity, AsyncBlock> pool;

	public ActivityAsyncPool() {
	    pool = new HashMap<Activity, AsyncBlock>();
	}

	public boolean isRunning(Activity fromActivity) {
	    Iterator it = pool.entrySet().iterator();
	    Activity activity;
	    AsyncBlock block;
	    boolean isBlockRunning = false;
	    
	    while(it.hasNext() && !isBlockRunning) {
		Map.Entry pairs = (Map.Entry)it.next();
		activity = (Activity)pairs.getKey();
		if (fromActivity == activity) {
		    block = (AsyncBlock)pairs.getValue();
		    isBlockRunning = block.isRunning();
		}
	    }

	    return isBlockRunning;
	}

	public boolean isRunning() {
	    Iterator it = pool.entrySet().iterator();
	    AsyncBlock block;
	    boolean isBlockRunning = false;
	    
	    while(it.hasNext() && !isBlockRunning) {
		Map.Entry pairs = (Map.Entry)it.next();
		block = (AsyncBlock)pairs.getValue();
		isBlockRunning = block.isRunning();
	    }

	    return isBlockRunning;
	}

	public void cancelTask() {
	    Iterator it = pool.entrySet().iterator();
	    AsyncBlock block;

	    while(it.hasNext()) {
		Map.Entry pairs = (Map.Entry)it.next();
		block = (AsyncBlock)pairs.getValue();
		block.cancelTask();
	    }
	}

	public void cancelTask(Activity fromActivity) {
	    Iterator it = pool.entrySet().iterator();
	    AsyncBlock block;
	    Activity activity;

	    while(it.hasNext()) {
		Map.Entry pairs = (Map.Entry)it.next();
		activity = (Activity)pairs.getKey();
		if (fromActivity == activity) {
		    block = (AsyncBlock)pairs.getValue();
		    block.cancelTask();
		    return;
		}
	    }
	}

	public void putHttpAsyncTask(Activity activity, HttpAsyncTask task) {
	    AsyncBlock block = pool.get(activity);
	    if (block == null)
		block = new AsyncBlock(activity);

	    block.putTask(task);
	    pool.put(activity, block);
	}

	public boolean isRequestAlreadyExecute(Activity activity, HttpRequest request) {
	    AsyncBlock block = pool.get(activity);
	    if (block == null)
		return false;
	    else {
		return block.isRequestAlreadyExecute(request);
	    }
	}

	public void putAndRunRequest(Activity activity, HttpRequest request, Requestable requestable) {
	    Log.d("=====", "" + isRequestAlreadyExecute(activity, request));
	    if (!isRequestAlreadyExecute(activity, request)) {
		HttpAsyncTask task = new HttpAsyncTask(activity, request, requestable);
		putHttpAsyncTask(activity, task);
		task.execute();
	    }
	}

	public void requestStopSpinner(Activity activity) {
	    AsyncBlock block = pool.get(activity);
	    block.requestStopSpinner();
	}

	public void requestStartSpinner(Activity activity) {
	    AsyncBlock block = pool.get(activity);
	    block.requestStartSpinner();
	}
    }

    private class AsyncBlock {
	private Activity parent;
	private Spinner spinner;
	private int spinnerCount;
	private ArrayList<HttpAsyncTask> tasks;

	public AsyncBlock(Activity parent) {
	    this.parent = parent;
	    spinner = new NormalSpinner(parent);
	    tasks = new ArrayList<HttpAsyncTask>();
	}

	public void cancelTask() {
	    spinnerCount = 0;
	    spinner.stop();
	    Iterator itr = tasks.iterator();
	    while(itr.hasNext()) {
		((AsyncTask)itr.next()).cancel(true);
	    }
	    tasks.clear();
	}

	public boolean isRequestAlreadyExecute(HttpRequest request) {
	    Iterator itr = tasks.iterator();
	    boolean result = false;
	    HttpAsyncTask task;
	    HttpRequest prevRequest;
	    while(itr.hasNext() && !result) {
		task = (HttpAsyncTask)itr.next();
		prevRequest = task.getRequest();
		result = prevRequest.isEqual(request);
	    }
	    return result;
	}

	public void putTask(HttpAsyncTask task) {
	    tasks.add(task);
	}

	public void requestStopSpinner() {
	    spinnerCount--;
	    if (spinnerCount <= 0) {
		spinner.stop();
		tasks.clear();
	    }
	}

	public void requestStartSpinner() { 
	}

	public boolean isRunning() {
	    Iterator itr = tasks.iterator();
	    boolean isTaskRunning = false;
	    while(itr.hasNext() && !isTaskRunning) {
		isTaskRunning = (AsyncTask.Status.FINISHED != ((AsyncTask)itr.next()).getStatus());
	    }

	    return isTaskRunning;
	}
    }
}