package com.matji.sandwich.http;

import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.http.spinner.Spinner;
import com.matji.sandwich.http.spinner.SpinnerFactory;

import java.util.HashMap;

public class HttpRequestManager {
    private volatile static HashMap<Context, HttpRequestManager> managerPool = new HashMap<Context, HttpRequestManager>();
    private Context context;
    private TaskQueueManager queueManager;
    private SpinnerFactory spinnerFactory;
    private boolean isTurnOff;

    private HttpRequestManager(Context context) {
	setContext(context);
	queueManager = TaskQueueManager.getInstance();
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

    public void request(ViewGroup layout, RequestCommand request, int tag, Requestable requestable) {
	request(layout, SpinnerFactory.SpinnerType.NORMAL, request, tag, requestable);
    }

    public void request(ViewGroup layout,
			SpinnerFactory.SpinnerType spinnerType,
			RequestCommand request, int tag, Requestable requestable) {
	if (isTurnOff) {
	    return ;
	}

	ManagerAsyncTask task = new ManagerAsyncTask(request, requestable, tag);
	Spinner spinner = spinnerFactory.createSpinner(context, spinnerType, layout);
	TaskElement element = new TaskElement(spinner, task);
	
	queueManager.offer(element);
	queueManager.start();
    }

    public boolean isRunning() {
	return queueManager.isRunning();
    }

    public void cancelTask() {
	queueManager.cancelTask();
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