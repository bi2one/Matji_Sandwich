package com.matji.sandwich.exception;

import com.matji.sandwich.R;

public class NotSupportedMatjiException extends MatjiException {
    /**
     * 
     */
    private static final long serialVersionUID = 748818989677517663L;

    public NotSupportedMatjiException() {
	super(R.string.exception_NotSupportedMatjiException);
    }
}
