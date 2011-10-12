package com.matji.sandwich.exception;

import com.matji.sandwich.R;

public class InternalServerMatjiException extends MatjiException {
    /**
     * 
     */
    private static final long serialVersionUID = -4160088427570993960L;

    public InternalServerMatjiException() {
	super(R.string.exception_InternalServerMatjiException);
    }
}
