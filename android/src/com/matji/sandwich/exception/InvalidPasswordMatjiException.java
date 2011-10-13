package com.matji.sandwich.exception;

import com.matji.sandwich.R;

public class InvalidPasswordMatjiException extends SimpleDialogShowingMatjiException {
	/**
     * 
     */
    private static final long serialVersionUID = 8413791156725169579L;

    public InvalidPasswordMatjiException() {
		super(R.string.exception_InvalidPasswordMatjiException);
	}
} 
