package com.matji.sandwich.exception;

public class GpsOutOfServiceMatjiException extends MatjiException {
    public GpsOutOfServiceMatjiException() {
	setToastMsg("GPS 서비스 지역에서 벗어났습니다");
    }
}
