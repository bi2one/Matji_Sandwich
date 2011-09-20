package com.matji.sandwich.http;

import android.util.Log;

import java.util.Stack;

public class TaskStackManager implements TaskElement.ProgressListener {
    private volatile static TaskStackManager manager;
    private Stack<TaskElement> taskStack;

    private TaskStackManager() {
	taskStack = new Stack<TaskElement>();
    }

    public static TaskStackManager getInstance() {
	if (manager == null) {
	    synchronized(TaskStackManager.class) {
		if (manager == null) {
		    manager = new TaskStackManager();
		}
	    }
	}
	return manager;
    }

    public synchronized void cancelTask() {
	TaskElement lastElement = taskStack.peek();
	lastElement.stop();
	lastElement = null;
	
	taskStack.clear();
    }

    public synchronized void start(TaskElement element) {
	if (!taskStack.empty()) {
	    TaskElement lastElement = taskStack.peek();
	    lastElement.setProgressListener(null);
	    lastElement.stop();
	}

	taskStack.push(element);
	element.setProgressListener(this);
	element.start();
    }

    private synchronized void startNextTask() {
	taskStack.pop();
	start(taskStack.pop());
    }

    public boolean isRunning() {
	return !taskStack.empty();
    }

    public void onStart(TaskElement element) {
    	// Log.d("=====", "start : " + element.toString());
    }
    
    public void onFinish(TaskElement element) {
    	// Log.d("=====", "finish: " + element.toString());
    	startNextTask();
    }
    
    public void onCancel(TaskElement element) {
    	// Log.d("=====", "cancel: " + element.toString());
    	startNextTask();
    }
}