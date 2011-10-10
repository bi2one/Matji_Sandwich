package com.matji.sandwich.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.ViewGroup;

import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.R;

import java.util.ArrayList;
import java.lang.ref.WeakReference;

public class DialogAsyncTask extends AsyncTask<Object, Integer, Boolean> {
    private ProgressDialog dialog;
    private WeakReference<Requestable> requestableRef;
    private ViewGroup spinnerContainer;
    private RequestCommand request;
    private int tag;
    private ArrayList<MatjiData> data;
    private MatjiException exception;
    private WeakReference<ProgressListener> listenerRef;

    public DialogAsyncTask(Context context, Requestable requestable, RequestCommand request, int tag) {
	this(context, requestable, null, request, tag);
    }

    public DialogAsyncTask(Context context, Requestable requestable, ViewGroup spinnerContainer, RequestCommand request, int tag) {
        this.requestableRef = new WeakReference(requestable);
        this.spinnerContainer = spinnerContainer;
	this.request = request;
	this.tag = tag;
	dialog = new ProgressDialog(context);
        dialog.setMessage(MatjiConstants.string(R.string.dialog_async_task));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    public void execute() {
	execute(new Object());
    }

    public void setDialogString(int resId) {
	dialog.setMessage(MatjiConstants.string(resId));
    }

    public void setProgressListener(ProgressListener listener) {
	listenerRef = new WeakReference(listener);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
	if (listenerRef != null && listenerRef.get() != null) {
	    listenerRef.get().onPreExecute(tag);
	}
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Object... arg0) {
	boolean result = true;
	if (listenerRef != null && listenerRef.get() != null) {
	    listenerRef.get().onStartBackground(tag);
	}
	
	try {
	    data = request.request();
	    result = true;
	} catch(MatjiException e) {
	    exception = e;
	    result = false;
	}
	
	if (listenerRef != null && listenerRef.get() != null) {
	    listenerRef.get().onEndBackground(tag);
	}
	return result;
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
	if (listenerRef != null && listenerRef.get() != null) {
	    listenerRef.get().onPostExecute(tag);
	}
	
        dialog.dismiss();
        
        if (isSuccess) {
	    requestableRef.get().requestCallBack(tag, data);
        } else {
	    requestableRef.get().requestExceptionCallBack(tag, exception);
        }
    }

    public interface ProgressListener {
	public void onPreExecute(int tag);
	public void onStartBackground(int tag);
	public void onEndBackground(int tag);
	public void onPostExecute(int tag);
    }
}
