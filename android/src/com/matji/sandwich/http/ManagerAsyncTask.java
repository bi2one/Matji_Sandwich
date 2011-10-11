package com.matji.sandwich.http;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.RequestCommand;

public class ManagerAsyncTask extends AsyncTask<RequestCommand, Integer, ArrayList<MatjiData>> {
    private RequestStatusListener listener;
    private RequestCommand command;
    private Requestable requestable;
    private int tag;
    private MatjiException occuredException;
    private boolean errorOccured;

    public ManagerAsyncTask(RequestCommand command, Requestable requestable, int tag) {
	this.command = command;
	this.requestable = requestable;
	this.tag = tag;
    }

    public void execute() {
	super.execute(command);
    }

    public void onPreExecute() {
	if (listener != null)
	    listener.onStartRequest(this);
    }

    public void setRequestStatusListener(RequestStatusListener listener) {
	this.listener = listener;
    }

    public void forceCancel() {
	cancel(true);
	command.cancel();
    }

    protected ArrayList<MatjiData> doInBackground(RequestCommand... params) {
    	try {
	    ArrayList<MatjiData> result = command.request();
	    errorOccured = false;
    	    return result;
    	} catch(MatjiException e) {
    	    occuredException = e;
	    errorOccured = true;
    	    return null;
    	}
    }

    protected void onCancelled() {
	if (listener != null)
	    listener.onCancelRequest(this);
    }

    protected void onPostExecute(ArrayList<MatjiData> result) {
	if (requestable != null) {
	    if (errorOccured) {
		requestable.requestExceptionCallBack(tag, occuredException);
	    } else {
		requestable.requestCallBack(tag, result);
	    }
	}

	if (listener != null)
	    listener.onFinishRequest(this);
    }
    
    public interface RequestStatusListener {
	public void onStartRequest(ManagerAsyncTask task);
	public void onFinishRequest(ManagerAsyncTask task);
	public void onCancelRequest(ManagerAsyncTask task);
    }
}
