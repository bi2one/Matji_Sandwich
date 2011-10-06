package com.matji.sandwich.exception;

import android.content.Context;
import android.widget.Toast;

import com.matji.sandwich.util.MatjiConstants;

public class MatjiException extends Exception {
    private int msgRef;
    private ToastPool toastPool;

    public MatjiException(int msgRef) {
	setMsg(msgRef);
	toastPool = ToastPool.getInstance();
    }

    protected void setMsg(int msgRef) {
	this.msgRef = msgRef;
    }

    public int getMsg() {
	return msgRef;
    }
    
    public String getMsgString() {
    	return MatjiConstants.string(getMsg());
    }

    public void showToastMsg(Context context) {
	Toast toast = toastPool.getToast(context, getMsg(), Toast.LENGTH_SHORT);
	toast.show();
    }

    public void performExceptionHandling(Context context) {
	showToastMsg(context);
    }
}
