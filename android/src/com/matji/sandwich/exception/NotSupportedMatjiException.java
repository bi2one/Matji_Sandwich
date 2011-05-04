package com.matji.sandwich.exception;

public class NotSupportedMatjiException extends MatjiException {
    public NotSupportedMatjiException() {
	setToastMsg("지원하지 않는 기능입니다");
    }
}
