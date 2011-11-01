package com.matji.sandwich.exception;

import com.matji.sandwich.R;

public class EmailNotExistMatjiException extends SimpleDialogShowingMatjiException {
    /**
     * 
     */
    private static final long serialVersionUID = 5470772605885834403L;

    public EmailNotExistMatjiException() {
        super(R.string.exception_EmailNotExistMatjiException);
	}
} 
