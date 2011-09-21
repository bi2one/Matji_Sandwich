package com.matji.sandwich.http;

import java.util.HashMap;

import android.content.Context;
import android.view.ViewGroup;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.http.spinner.Spinnable;
import com.matji.sandwich.http.spinner.SpinnerFactory;

public class HttpRequestManager {
    // request tag
    public static final int AUTHORIZE = 0;
    public static final int BADGE = 1;
    public static final int LIKE_REQUEST = 10;
    public static final int UN_LIKE_REQUEST = 11;
    public static final int FOLLOW_REQUEST = 20;
    public static final int UN_FOLLOW_REQUEST = 21;
    public static final int BOOKMAR_REQUEST = 30;
    public static final int UN_BOOKMARK_REQUEST = 31;
    public static final int POST_SHOW_REQUEST = 41;
    public static final int STORE_TAG_LIST_REQUEST = 52;
    public static final int USER_TAG_LIST_REQUEST = 62;
    public static final int USER_UPDATE_REQUEST = 63;
    public static final int MESSAGE_NEW_REQUEST = 70;
    public static final int MESSAGE_READ_REQUEST = 71;
    public static final int ALARM_READ_REQUEST = 81;
    public static final int UPDATE_ALARM_PERMIT_REQUEST = 82;

    private volatile static HashMap<Context, HttpRequestManager> managerPool = new HashMap<Context, HttpRequestManager>();
    private Context context;
    private TaskPoolManager taskPoolManager;
    // private TaskQueueManager queueManager;
    private SpinnerFactory spinnerFactory;
    private boolean isTurnOff;

    private HttpRequestManager(Context context) {
	setContext(context);
	taskPoolManager = TaskPoolManager.getInstance(context);
	// queueManager = TaskQueueManager.getInstance();
	spinnerFactory = new SpinnerFactory();
	isTurnOff = false;
    }

    public static HttpRequestManager getInstance(Context context) {
	HttpRequestManager manager = managerPool.get(context);
	
	if (manager == null) {
	    synchronized(HttpRequestManager.class) {
		if (manager == null) {
		    manager = new HttpRequestManager(context);
		    managerPool.put(context, manager);
		}
	    }
	}
	return manager;
    }

    public void request(RequestCommand request) {
	request(null, SpinnerFactory.SpinnerType.NORMAL, request, 0, null);
    }

    public void request(RequestCommand request, int tag, Requestable requestable) {
	request(null, SpinnerFactory.SpinnerType.NORMAL, request, tag, requestable);
    }

    public void request(ViewGroup layout, RequestCommand request) {
	request(layout, SpinnerFactory.SpinnerType.NORMAL, request, 0, null);
    }

    public void request(ViewGroup layout, RequestCommand request, int tag, Requestable requestable) {
	request(layout, SpinnerFactory.SpinnerType.NORMAL, request, tag, requestable);
    }

    public void request(ViewGroup layout,
			SpinnerFactory.SpinnerType spinnerType,
			RequestCommand request, int tag, Requestable requestable) {
	request(SpinnerFactory.createSpinner(context, spinnerType, layout),
		request, tag, requestable);
    }

    public void request(Spinnable spinner,
			RequestCommand request,
			int tag,
			Requestable requestable) {
	if (isTurnOff) {
	    return ;
	}

	ManagerAsyncTask task = new ManagerAsyncTask(request, requestable, tag);
	TaskElement element = new TaskElement(spinner, task);
	
	// queueManager.offer(element);
	// queueManager.start();
	taskPoolManager.start(element);
    }

    public boolean isRunning() {
	return taskPoolManager.isRunning();
	// return queueManager.isRunning();
    }

    public void cancelTask() {
	taskPoolManager.cancelTask();
	// queueManager.cancelTask();
    }

    public void cancelAllTask() {
    	Collection<HttpRequestManager> managerCollection = managerPool.values();
	for (HttpRequestManager manager : managerCollection) {
	    manager.cancelTask();
	}
    }

    /**
     * Manager에 들어오는 request요청들을 전부 수행하지 않도록 꺼둔다
     */
    public void turnOff() {
	cancelTask();
	isTurnOff = true;
    }

    /**
     * Manager에 들어오는 request요청들을 수행할 수 있도록 다시 켠다
     */
    public void turnOn() {
	cancelTask();
	isTurnOff = false;
    }

    private void setContext(Context context) {
	this.context = context;
    }
}