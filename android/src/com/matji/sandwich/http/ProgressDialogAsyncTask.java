package com.matji.sandwich.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.util.Log;

import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.http.request.ProgressRequestCommand;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.listener.ProgressListener;
import com.matji.sandwich.R;

import java.util.ArrayList;

public class ProgressDialogAsyncTask extends AsyncTask<Object, Integer, Boolean> implements ProgressListener {
    private static final int PRIMARY = 0;
    private static final int SECONDARY = 1;
    private ProgressDialog progressDialog;
    private Context context;
    private Requestable requestable;
    private ProgressRequestCommand request;
    private int tag;
    private ArrayList<MatjiData> data;
    private MatjiException exception;

    public ProgressDialogAsyncTask(Context context, Requestable requestable, ProgressRequestCommand request, int tag) {
	this.context = context;
	this.requestable = requestable;
	this.request = request;
	this.tag = tag;

	progressDialog = new ProgressDialog(context);
	progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	setMessage(R.string.dialog_intro_loading);
	progressDialog.setCancelable(false);
	progressDialog.setProgress(0);
    }

    public void setMessage(int strId) {
	progressDialog.setMessage(MatjiConstants.string(strId));
    }

    public void setMessage(String msg) {
	progressDialog.setMessage(msg);
    }

    public void execute() {
	execute(new Object());
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
	request.setProgressListener(tag, this);
	progressDialog.setMax(request.getRequestCount());
        progressDialog.show();
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
        progressDialog.dismiss();
	request.setProgressListener(tag, null);
        
        if (isSuccess) {
	    requestable.requestCallBack(tag, data);
        } else {
	    requestable.requestExceptionCallBack(tag, exception);
        }
    }

    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
	int which = values[0];
	int tag = values[0];
	int total = values[1];
	int read = values[2];
	int progress = (int)((float)read / (float)total * 1000f);

	switch(which) {
	case PRIMARY:
	    progressDialog.setProgress(progress);
	    break;
	case SECONDARY:
	    progressDialog.setSecondaryProgress(progress);
	    break;
	}
    }

    public void onUnitWritten(int tag, int totalCount, int readCount) {
	publishProgress(PRIMARY, tag, totalCount, readCount);
    }

    public void onWritten(int tag, int totalBytes, int readBytes) {
	publishProgress(SECONDARY, tag, totalBytes, readBytes);
    }
}
