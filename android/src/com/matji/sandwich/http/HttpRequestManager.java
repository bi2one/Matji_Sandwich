//package com.matji.sandwich.http;
//
//import android.os.AsyncTask;
//import android.app.Activity;
//import android.util.Log;
//import android.content.Context;
//
//import com.matji.sandwich.Requestable;
//import com.matji.sandwich.widget.Spinner;
//import com.matji.sandwich.widget.NormalSpinner;
//import com.matji.sandwich.http.request.HttpRequest;
//import com.matji.sandwich.http.request.RequestCommand;
//import com.matji.sandwich.data.MatjiData;
//import com.matji.sandwich.exception.MatjiException;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Stack;
//import java.util.Iterator;
//import java.util.Map;
//
//public class HttpRequestManager {
//    private static volatile HttpRequestManager manager;
//    private boolean isRunnable = true;
//    private Context context;
//    private Activity mParentActivity;
//    private ActivityAsyncPool asyncPool;
//
//    private HttpRequestManager(Context context) {
//	this.context = context;
//	asyncPool = new ActivityAsyncPool();
//    }
//
//    public static HttpRequestManager getInstance(Context context) {
//	if (manager != null && manager.context != context)
//	    manager.context = context;
//
//	if (manager == null) {
//	    synchronized(HttpRequestManager.class) {
//		if (manager == null) {
//		    manager = new HttpRequestManager(context);
//		}
//	    }
//	}
//
//	return manager;
//    }
//
//    public void request(Activity parentActivity, RequestCommand request, int tag, Requestable requestable) {
//	// request.setTag(tag);
//	if (isRunnable) {
//	    asyncPool.putAndRunRequest(parentActivity, request, requestable, tag);
//	}
//    }
//
//    public boolean isRunning(Activity parentActivity) {
//	return asyncPool.isRunning(parentActivity);
//    }
//
//    public boolean isRunning() {
//	return asyncPool.isRunning();
//    }
//
//    public void cancelTask() {
//	asyncPool.cancelTask();
//    }
//
//    public void cancelTask(Activity parentActivity) {
//	asyncPool.cancelTask(parentActivity);
//    }
//
//    /**
//     * Manager에 들어오는 request요청들을 전부 수행하지 않도록 꺼둔다
//     */
//    public void turnOff() {
//	cancelTask();
//	isRunnable = false;
//    }
//
//    /**
//     * Manager에 들어오는 request요청들을 수행할 수 있도록 다시 켠다
//     */
//    public void turnOn() {
//	cancelTask();
//	isRunnable = true;
//    }
//
//    private class HttpAsyncTask extends AsyncTask<RequestCommand, Integer, ArrayList<MatjiData>> {
//	protected int tag;
//	protected RequestCommand request;
//	private Activity parentActivity;
//	private MatjiException occuredException;
//	private Requestable requestable;
//
//	public HttpAsyncTask(Activity activity, RequestCommand request, Requestable requestable, int tag) {
//	    parentActivity = activity;
//	    this.tag = tag;
//	    this.request = request;
//	    this.requestable = requestable;
//	}
//
//	public void execute() {
//	    execute(request);
//	}
//
//	public RequestCommand getRequest() {
//	    return request;
//	}
//
//	protected ArrayList<MatjiData> doInBackground(RequestCommand... params) {
//	    ArrayList<MatjiData> result;
//	    Log.d("refresh", "doin " + params.length);
//
//	    try {
//		result = request.request();
//	    } catch(MatjiException e) {
//		occuredException = e;
//		return null;
//	    }
//	    
//	    Log.d("refresh", "doin end");
//	    return result;
//	}
//
//	protected void onCancelled() {
//	    request.cancel();
//	    asyncPool.requestStopSpinner(parentActivity);
//	}
//
//	protected void onPostExecute(ArrayList<MatjiData> result) {
//	    if (result == null) {
//		requestable.requestExceptionCallBack(tag, occuredException);
//	    } else {
//		requestable.requestCallBack(tag, result);
//	    }
//	    
//	    asyncPool.requestStopSpinner(parentActivity);
//	    Log.d("refresh", "onPostExecute");
//	    Log.d("refresh", "onPostExecute end");
//	}
//
//	protected void onPreExecute() {
//	    asyncPool.requestStartSpinner(parentActivity);
//	}
//
//	protected void onProgressUpdate(Integer... values) { }
//    }
//
//    private class ActivityAsyncPool {
//	private HashMap<Activity, AsyncBlock> pool;
//
//	public ActivityAsyncPool() {
//	    pool = new HashMap<Activity, AsyncBlock>();
//	}
//
//	public boolean isRunning(Activity fromActivity) {
//	    Iterator it = pool.entrySet().iterator();
//	    Activity activity;
//	    AsyncBlock block;
//	    boolean isBlockRunning = false;
//	    
//	    while(it.hasNext() && !isBlockRunning) {
//		Map.Entry pairs = (Map.Entry)it.next();
//		activity = (Activity)pairs.getKey();
//		if (fromActivity == activity) {
//		    block = (AsyncBlock)pairs.getValue();
//		    isBlockRunning = block.isRunning();
//		}
//	    }
//
//	    return isBlockRunning;
//	}
//
//	public boolean isRunning() {
//	    Iterator it = pool.entrySet().iterator();
//	    AsyncBlock block;
//	    boolean isBlockRunning = false;
//	    
//	    while(it.hasNext() && !isBlockRunning) {
//		Map.Entry pairs = (Map.Entry)it.next();
//		block = (AsyncBlock)pairs.getValue();
//		isBlockRunning = block.isRunning();
//	    }
//
//	    return isBlockRunning;
//	}
//
//	public void cancelTask() {
//	    Iterator it = pool.entrySet().iterator();
//	    AsyncBlock block;
//
//	    while(it.hasNext()) {
//		Map.Entry pairs = (Map.Entry)it.next();
//		block = (AsyncBlock)pairs.getValue();
//		block.cancelTask();
//	    }
//	}
//
//	public void cancelTask(Activity fromActivity) {
//	    Iterator it = pool.entrySet().iterator();
//	    AsyncBlock block;
//	    Activity activity;
//
//	    while(it.hasNext()) {
//		Map.Entry pairs = (Map.Entry)it.next();
//		activity = (Activity)pairs.getKey();
//		if (fromActivity == activity) {
//		    block = (AsyncBlock)pairs.getValue();
//		    block.cancelTask();
//		    return;
//		}
//	    }
//	}
//
//	public void putHttpAsyncTask(Activity activity, HttpAsyncTask task) {
//	    AsyncBlock block = pool.get(activity);
//	    if (block == null)
//		block = new AsyncBlock(activity);
//
//	    block.putTask(task);
//	    pool.put(activity, block);
//	}
//
//	public boolean isRequestAlreadyExecute(Activity activity, RequestCommand request) {
//	    AsyncBlock block = pool.get(activity);
//	    if (block == null)
//		return false;
//	    else {
//		return block.isRequestAlreadyExecute(request);
//	    }
//	}
//
//	public void putAndRunRequest(Activity activity, RequestCommand request, Requestable requestable, int tag) {
//	    // Log.d("=====", "" + isRequestAlreadyExecute(activity, request));
//	    if (!isRequestAlreadyExecute(activity, request)) {
//		HttpAsyncTask task = new HttpAsyncTask(activity, request, requestable, tag);
//		putHttpAsyncTask(activity, task);
//		task.execute();
//	    }
//	}
//
//	public void requestStopSpinner(Activity activity) {
//	    AsyncBlock block = pool.get(activity);
//	    block.requestStopSpinner();
//	}
//
//	public void requestStartSpinner(Activity activity) {
//	    AsyncBlock block = pool.get(activity);
//	    block.requestStartSpinner();
//	}
//    }
//
//    private class AsyncBlock {
//	private Activity parent;
//	private Spinner spinner;
//	private int spinnerCount;
//	private ArrayList<HttpAsyncTask> tasks;
//
//	public AsyncBlock(Activity parent) {
//	    this.parent = parent;
//	    spinner = new NormalSpinner(parent);
//	    tasks = new ArrayList<HttpAsyncTask>();
//	}
//
//	public void cancelTask() {
//	    spinnerCount = 0;
//	    spinner.stop();
//	    Iterator itr = tasks.iterator();
//	    while(itr.hasNext()) {
//		((AsyncTask)itr.next()).cancel(true);
//	    }
//	    tasks.clear();
//	}
//
//	public boolean isRequestAlreadyExecute(RequestCommand request) {
//	    Iterator itr = tasks.iterator();
//	    boolean result = false;
//	    HttpAsyncTask task;
//	    RequestCommand prevRequest;
//	    while(itr.hasNext() && !result) {
//		task = (HttpAsyncTask)itr.next();
//		prevRequest = task.getRequest();
//		result = prevRequest.equals(request);
//	    }
//	    return result;
//	}
//
//	public void putTask(HttpAsyncTask task) {
//	    tasks.add(task);
//	}
//
//	public void requestStopSpinner() {
//	    spinnerCount--;
//	    if (spinnerCount <= 0) {
//		spinner.stop();
//		tasks.clear();
//	    }
//	}
//
//	public void requestStartSpinner() {
//	    spinnerCount++;
//	    spinner.start(parent);
//	}
//
//	public boolean isRunning() {
//	    Iterator itr = tasks.iterator();
//	    boolean isTaskRunning = false;
//	    while(itr.hasNext() && !isTaskRunning) {
//		isTaskRunning = (AsyncTask.Status.FINISHED != ((AsyncTask)itr.next()).getStatus());
//	    }
//
//	    return isTaskRunning;
//	}
//    }
//}


package com.matji.sandwich.http;

import android.os.AsyncTask;
import android.util.Log;
import android.content.Context;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.widget.Spinner;
import com.matji.sandwich.widget.NormalSpinner;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Iterator;
import java.util.Map;

public class HttpRequestManager {
    private static volatile HttpRequestManager manager;
    private boolean isRunnable = true;
    private Context context;
    private Context mParentContext;
    private ContextAsyncPool asyncPool;

    private HttpRequestManager(Context context) {
	this.context = context;
	asyncPool = new ContextAsyncPool();
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

    public void request(Context parentContext, RequestCommand request, int tag, Requestable requestable) {
	// request.setTag(tag);
	if (isRunnable) {
	    asyncPool.putAndRunRequest(parentContext, request, requestable, tag);
	}
    }

    public boolean isRunning(Context context) {
	return asyncPool.isRunning(context);
    }

    public boolean isRunning() {
	return asyncPool.isRunning();
    }

    public void cancelTask() {
	asyncPool.cancelTask();
    }

    public void cancelTask(Context parentContext) {
	asyncPool.cancelTask(parentContext);
    }

    /**
     * Manager에 들어오는 request요청들을 전부 수행하지 않도록 꺼둔다
     */
    public void turnOff() {
	cancelTask();
	isRunnable = false;
    }

    /**
     * Manager에 들어오는 request요청들을 수행할 수 있도록 다시 켠다
     */
    public void turnOn() {
	cancelTask();
	isRunnable = true;
    }

    private class HttpAsyncTask extends AsyncTask<RequestCommand, Integer, ArrayList<MatjiData>> {
	protected int tag;
	protected RequestCommand request;
	private Context parentContext;
	private MatjiException occuredException;
	private Requestable requestable;

	public HttpAsyncTask(Context context, RequestCommand request, Requestable requestable, int tag) {
	    parentContext = context;
	    this.tag = tag;
	    this.request = request;
	    this.requestable = requestable;
	}

	public void execute() {
	    execute(request);
	}

	public RequestCommand getRequest() {
	    return request;
	}

	protected ArrayList<MatjiData> doInBackground(RequestCommand... params) {
	    ArrayList<MatjiData> result;
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
	    request.cancel();
	    asyncPool.requestStopSpinner(parentContext);
	}

	protected void onPostExecute(ArrayList<MatjiData> result) {
	    if (result == null) {
		requestable.requestExceptionCallBack(tag, occuredException);
	    } else {
		requestable.requestCallBack(tag, result);
	    }
	    
	    asyncPool.requestStopSpinner(parentContext);
	    Log.d("refresh", "onPostExecute");
	    Log.d("refresh", "onPostExecute end");
	}

	protected void onPreExecute() {
	    asyncPool.requestStartSpinner(parentContext);
	}

	protected void onProgressUpdate(Integer... values) { }
    }

    private class ContextAsyncPool {
	private HashMap<Context, AsyncBlock> pool;

	public ContextAsyncPool() {
	    pool = new HashMap<Context, AsyncBlock>();
	}

	public boolean isRunning(Context fromContext) {
	    Iterator it = pool.entrySet().iterator();
	    Context context;
	    AsyncBlock block;
	    boolean isBlockRunning = false;
	    
	    while(it.hasNext() && !isBlockRunning) {
		Map.Entry pairs = (Map.Entry)it.next();
		context = (Context)pairs.getKey();
		if (fromContext == context) {
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

	public void cancelTask(Context fromContext) {
	    Iterator it = pool.entrySet().iterator();
	    AsyncBlock block;
	    Context context;

	    while(it.hasNext()) {
		Map.Entry pairs = (Map.Entry)it.next();
		context = (Context)pairs.getKey();
		if (fromContext == context) {
		    block = (AsyncBlock)pairs.getValue();
		    block.cancelTask();
		    return;
		}
	    }
	}

	public void putHttpAsyncTask(Context context, HttpAsyncTask task) {
	    AsyncBlock block = pool.get(context);
	    if (block == null)
		block = new AsyncBlock(context);

	    block.putTask(task);
	    pool.put(context, block);
	}

	public boolean isRequestAlreadyExecute(Context context, RequestCommand request) {
	    AsyncBlock block = pool.get(context);
	    if (block == null)
		return false;
	    else {
		return block.isRequestAlreadyExecute(request);
	    }
	}

	public void putAndRunRequest(Context context, RequestCommand request, Requestable requestable, int tag) {
	    // Log.d("=====", "" + isRequestAlreadyExecute(activity, request));
	    if (!isRequestAlreadyExecute(context, request)) {
		HttpAsyncTask task = new HttpAsyncTask(context, request, requestable, tag);
		putHttpAsyncTask(context, task);
		task.execute();
	    }
	}

	public void requestStopSpinner(Context context) {
	    AsyncBlock block = pool.get(context);
	    block.requestStopSpinner();
	}

	public void requestStartSpinner(Context context) {
	    AsyncBlock block = pool.get(context);
	    block.requestStartSpinner();
	}
    }

    private class AsyncBlock {
	private Context parent;
	private Spinner spinner;
	private int spinnerCount;
	private ArrayList<HttpAsyncTask> tasks;

	public AsyncBlock(Context parent) {
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

	public boolean isRequestAlreadyExecute(RequestCommand request) {
	    Iterator itr = tasks.iterator();
	    boolean result = false;
	    HttpAsyncTask task;
	    RequestCommand prevRequest;
	    while(itr.hasNext() && !result) {
		task = (HttpAsyncTask)itr.next();
		prevRequest = task.getRequest();
		result = prevRequest.equals(request);
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
	    spinnerCount++;
	    spinner.start(parent);
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