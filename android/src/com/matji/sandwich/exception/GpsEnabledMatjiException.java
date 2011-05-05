package com.matji.sandwich.exception;

public class GpsEnabledMatjiException extends MatjiException {
    public GpsEnabledMatjiException() {
	setToastMsg("GPS서비스를 사용할 수 있습니다");
    }
}