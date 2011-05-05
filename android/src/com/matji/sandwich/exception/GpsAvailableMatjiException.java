package com.matji.sandwich.exception;

public class GpsAvailableMatjiException extends MatjiException {
    public GpsAvailableMatjiException() {
	setToastMsg("GPS가 정상작동 합니다");
    }
}
