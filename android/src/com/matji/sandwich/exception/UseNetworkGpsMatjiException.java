package com.matji.sandwich.exception;

public class UseNetworkGpsMatjiException extends MatjiException {
    public UseNetworkGpsMatjiException() {
	setToastMsg("GPS수신이 불안정합니다. 네트워크로부터 현재 위치를 추정합니다");
    }
}
