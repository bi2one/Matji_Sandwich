package com.matji.sandwich.exception;

import android.content.Context;
import android.widget.Toast;

public class MatjiException extends Exception {
    private String toastMsg;
    private String name;

    protected void setToastMsg(String toastMsg) {
	this.toastMsg = toastMsg;
    }

    public String getToastMsg() {
	return toastMsg;
    }

    public void showToastMsg(Context context) {
	Toast toast = Toast.makeText(context, getToastMsg(), Toast.LENGTH_SHORT);
	toast.show();
    }

    public String getName() {
	return name;
    }

    protected void setName(String name) {
	this.name = name;
    }
}
