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

public class DialogAsyncTask extends AsyncTask<Object, Integer, Boolean> {
    private ProgressDialog dialog;
    private Context context;
    private Requestable requestable;
    private ViewGroup spinnerContainer;
    private RequestCommand request;
    private int tag;
    private ArrayList<MatjiData> data;
    private MatjiException exception;

    public DialogAsyncTask(Context context, Requestable requestable, RequestCommand request, int tag) {
	this(context, requestable, null, request, tag);
    }

    public DialogAsyncTask(Context context, Requestable requestable, ViewGroup spinnerContainer, RequestCommand request, int tag) {
        this.context = context;
        this.requestable = requestable;
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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Object... arg0) {
	try {
	    data = request.request();
	    return true;
	} catch(MatjiException e) {
	    exception = e;
	    return false;
	}
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        dialog.dismiss();
        
        if (isSuccess) {
	    requestable.requestCallBack(tag, data);
        } else {
	    requestable.requestExceptionCallBack(tag, exception);
        }
    }

    // @Override
    // protected void onProgressUpdate(Integer... values) {
    //     super.onProgressUpdate(values);
    // }
}
