package com.matji.sandwich.exception;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class ExceptionFactory {
	public static final int STATUS_OK = 200;
	public static final int INVALID_PASSWORD = 501;

	public static MatjiException create(int code) {
		MatjiException result = null;

		switch(code) {
		case INVALID_PASSWORD:
			result = new InvalidPasswordMatjiException();
		}
		
		return result;
	}
	
}
