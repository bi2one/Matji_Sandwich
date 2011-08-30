package com.matji.sandwich.http;

import com.matji.sandwich.http.spinner.Spinner;

public class TaskElement implements ManagerAsyncTask.RequestStatusListener {
    private ManagerAsyncTask task;
    private ProgressListener listener;
    private Spinner spinner;
    
    public TaskElement(Spinner spinner, ManagerAsyncTask task) {
	this.task = task;
	this.spinner = spinner;
	task.setRequestStatusListener(this);
    }

    public void setProgressListener(ProgressListener listener) {
	this.listener = listener;
    }

    public void start() {
	task.execute();
    }

    public void stop() {
	task.cancel(true);
    }

    public void onStartRequest(ManagerAsyncTask task) {
	spinner.start();
	if (listener != null) {
	    listener.onStart(this);
	}
    }

    public void onFinishRequest(ManagerAsyncTask task) {
	spinner.stop();
	// spinner = null;
	if (listener != null) {
	    listener.onFinish(this);
	}
    }

    public void onCancelRequest(ManagerAsyncTask task) {
	spinner.stop();
	// spinner = null;
	if (listener != null) {
	    listener.onCancel(this);
	}
    }

    public interface ProgressListener {
	public void onStart(TaskElement element);
	public void onFinish(TaskElement element);
	public void onCancel(TaskElement element);
    }
}