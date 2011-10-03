package com.matji.sandwich.http;

import android.util.Log;
import android.content.Context;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskPoolManager implements TaskElement.ProgressListener {
    private volatile static HashMap<Context, TaskPoolManager> managerPool = new HashMap();
    private ArrayList<TaskElement> runningTaskPool;

    private TaskPoolManager() {
	runningTaskPool = new ArrayList<TaskElement>();
    }

    public static TaskPoolManager getInstance(Context context) {
	TaskPoolManager manager = managerPool.get(context);
	if (manager == null) {
	    synchronized(TaskQueueManager.class) {
		if (manager == null) {
		    manager = new TaskPoolManager();
		    managerPool.put(context, manager);
		}
	    }
	}
	return manager;
    }

    public synchronized void cancelTask() {
	for (TaskElement element : runningTaskPool) {
	    element.stop();
	}
	runningTaskPool.clear();
    }

    public synchronized void start(TaskElement element) {
	Log.d("=====", "AsyncTask gooooooooooo");
	element.setProgressListener(this);
	element.start();
	runningTaskPool.add(element);
    }

    public boolean isRunning() {
	// Log.d("=====", "isRunning: " + !runningTaskPool.isEmpty());
	return !runningTaskPool.isEmpty();
    }

    public void onStart(TaskElement element) {
    	// Log.d("=====", "start : " + element.toString());
    }
    
    public void onFinish(TaskElement element) {
    	// Log.d("=====", "finish: " + element.toString());
	runningTaskPool.remove(element);
    }
    
    public void onCancel(TaskElement element) {
    	// Log.d("=====", "cancel: " + element.toString());
	runningTaskPool.remove(element);
    }
}