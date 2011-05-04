package com.matji.sandwich.exception;

public class HttpConnectMatjiException extends MatjiException {
    public HttpConnectMatjiException() {
	setToastMsg("접속 상태가 불량합니다. 접속상태를 확인하세요");
    }
}
