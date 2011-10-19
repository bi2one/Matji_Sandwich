package com.matji.sandwich.exception;

import com.matji.sandwich.R;

import android.content.Context;

public class UnknownClientErrorMatjiException extends MatjiException {
    /**
     * 
     */
    private static final long serialVersionUID = 5470772605885834403L;

    public UnknownClientErrorMatjiException() {
        super(R.string.exception_InternalServerMatjiException);
	}
  
    @Override
    public void performExceptionHandling(Context context) {}
} 
