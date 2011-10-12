package com.matji.sandwich.exception;

import android.content.Context;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class InvalidPasswordMatjiException extends MatjiException {
	/**
     * 
     */
    private static final long serialVersionUID = 8413791156725169579L;

    public InvalidPasswordMatjiException() {
		super(R.string.exception_InvalidPasswordMatjiException);
	}
	
	public void performExceptionHandling(Context context) {
		SimpleAlertDialog dialog = new SimpleAlertDialog(context, getMsg());
		dialog.show();
	}
} 
