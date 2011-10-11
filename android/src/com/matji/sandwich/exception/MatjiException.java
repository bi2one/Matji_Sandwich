package com.matji.sandwich.exception;

import android.content.Context;
import android.widget.Toast;

import com.matji.sandwich.util.MatjiConstants;

public class MatjiException extends Exception {
    private String message;
    private ToastPool toastPool;

    public MatjiException(int msgRef) {
	this(MatjiConstants.string(msgRef));
    }

    public MatjiException(String msg) {
	setMsg(msg);
	toastPool = ToastPool.getInstance();
    }

    protected void setMsg(String msg) {
	this.message = msg;
    }
    
    public String getMsg() {
	return message;
    }
    
    public void showToastMsg(Context context) {
	Toast toast = toastPool.getToast(context, getMsg(), Toast.LENGTH_SHORT);
	toast.show();
    }

    public void performExceptionHandling(Context context) {
	showToastMsg(context);
    }
}
