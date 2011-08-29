package com.matji.sandwich.http;

import android.util.Log;

import java.util.Queue;
import java.util.LinkedList;

public class TaskQueueManager implements TaskElement.ProgressListener {
    private volatile static TaskQueueManager manager;
    private Queue<TaskElement> taskQueue;
    private TaskElement runningTask;

    private TaskQueueManager() {
	taskQueue = new LinkedList<TaskElement>();
    }

    public static TaskQueueManager getInstance() {
	if (manager == null) {
	    synchronized(TaskQueueManager.class) {
		if (manager == null) {
		    return new TaskQueueManager();
		}
	    }
	}
	return manager;
    }

    public void offer(TaskElement element) {
	taskQueue.offer(element);
    }

    public TaskElement getRunningTask() {
	return runningTask;
    }

    public void cancelTask() {
	stop();
	taskQueue.clear();
    }

    public void stop() {
	if (runningTask != null) {
	    runningTask.stop();
	    runningTask = null;
	}
    }

    public void start() {
	if (runningTask == null) {
	    startNewTask();
	}
    }

    public void startNewTask() {
	runningTask = taskQueue.poll();
	if (runningTask != null) {
	    runningTask.setProgressListener(this);
	    runningTask.start();
	}
    }

    public boolean isRunning() {
	return runningTask != null;
    }

    public void onStart(TaskElement element) {
    	// Log.d("=====", "start : " + element.toString());
    }
    
    public void onFinish(TaskElement element) {
    	// Log.d("=====", "finish: " + element.toString());
    	startNewTask();
    }
    
    public void onCancel(TaskElement element) {
    	// Log.d("=====", "cancel: " + element.toString());
    	startNewTask();
    }
}