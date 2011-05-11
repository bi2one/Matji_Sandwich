package com.matji.sandwich.exception;

import com.matji.sandwich.R;

import android.widget.Toast;
import android.content.Context;

public class JSONCodeMatjiException extends MatjiException {
    String errMessage;
    
    public JSONCodeMatjiException(String message){
	super(R.string.exception_JSONCodeMatjiException);
	errMessage = message;
    }

    public void performExceptionHandling(Context context) {
	Toast.makeText(context, errMessage, Toast.LENGTH_SHORT).show();
    }
}
