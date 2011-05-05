package com.matji.sandwich.exception;

public class InterruptedMatjiException extends MatjiException {
    public InterruptedMatjiException() {
	setToastMsg("Interrupted Exception: 연산을 중지합니다");
    }
}
