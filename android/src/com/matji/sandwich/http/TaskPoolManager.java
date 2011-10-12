package com.matji.sandwich.http;

import java.util.ArrayList;

public class TaskPoolManager implements TaskElement.ProgressListener {
    private volatile static TaskPoolManager manager;
    private ArrayList<TaskElement> runningTaskPool;

    private TaskPoolManager() {
	runningTaskPool = new ArrayList<TaskElement>();
    }

    public static TaskPoolManager getInstance() {
	if (manager == null) {
	    synchronized(TaskPoolManager.class) {
		if (manager == null) {
		    manager = new TaskPoolManager();
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
	// Log.d("=====", "AsyncTask gooooooooooo");
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