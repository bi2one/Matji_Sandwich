package com.matji.sandwich.http;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.ProgressRequestCommand;
import com.matji.sandwich.listener.ProgressListener;
import com.matji.sandwich.util.MatjiConstants;

public class ProgressDialogAsyncTask extends AsyncTask<Object, Integer, Boolean> implements ProgressListener {
    private static final int UPDATE_BYTES = 0;
    private static final int UPDATE_COUNT = 1;
    private static final int TITLE_ID = R.string.progress_dialog;
    private static final int COUNTER_ID = R.string.progress_dialog_counter;
    private ProgressDialog progressDialog;
    private Requestable requestable;
    private ProgressRequestCommand request;
    private int tag;
    private ArrayList<MatjiData> data;
    private MatjiException exception;

    public ProgressDialogAsyncTask(Context context, Requestable requestable, ProgressRequestCommand request, int tag) {
        this.requestable = requestable;
        this.request = request;
        this.tag = tag;

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        setMessage(TITLE_ID);
        progressDialog.setCancelable(false);
//        progressDialog.setProgress(0);
    }

    private void setMessage(int titleId, int counterId, int totalCount, int readingCount) {
        String baseTitle = MatjiConstants.string(titleId);
        String progressTitle = String.format(MatjiConstants.string(counterId), readingCount, totalCount);
        if (totalCount > 0) {
            setMessage(baseTitle + " " + progressTitle);
        } else {
            setMessage(baseTitle);
        }
    }

    private void setMessage(int strId) {
        progressDialog.setMessage(MatjiConstants.string(strId));
    }

    private void setMessage(String msg) {
        progressDialog.setMessage(msg);
    }

    public void execute() {
        execute(new Object());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        request.setProgressListener(tag, this);
        setMessage(TITLE_ID, COUNTER_ID, request.getRequestCount(), 1);
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
        //	int tag = values[1];
        int total = values[2];
        int read = values[3];

        switch(which) {
        case UPDATE_BYTES:
            progressDialog.setMax(total);
            progressDialog.incrementProgressBy(read);
            break;
        case UPDATE_COUNT:
            if (total != read) {
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
                setMessage(TITLE_ID, COUNTER_ID, total, read + 1);
            }
            break;
        }
    }

    public void onUnitWritten(int tag, int totalCount, int readCount) {
        publishProgress(UPDATE_COUNT, tag, totalCount, readCount);
    }

    public void onWritten(int tag, int totalBytes, int readBytes) {
        publishProgress(UPDATE_BYTES, tag, totalBytes, readBytes);
    }
}
