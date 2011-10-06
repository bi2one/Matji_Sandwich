package com.matji.sandwich.exception;

import android.content.Context;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class InvalidPasswordMatjiException extends MatjiException {
	public InvalidPasswordMatjiException() {
		super(R.string.exception_InvalidPasswordMatjiException);
	}
	
	public void performExceptionHandling(Context context) {
		SimpleAlertDialog dialog = new SimpleAlertDialog(context, getMsgString());
		dialog.show();
	}
} 
