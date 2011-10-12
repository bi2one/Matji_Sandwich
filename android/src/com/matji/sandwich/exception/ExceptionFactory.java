package com.matji.sandwich.exception;


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
