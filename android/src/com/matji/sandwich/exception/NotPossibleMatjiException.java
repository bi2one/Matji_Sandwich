package com.matji.sandwich.exception;

public class NotPossibleMatjiException extends MatjiException {
    public NotPossibleMatjiException() {
	setToastMsg("일어날 수 없는 행동입니다");
    }
}
