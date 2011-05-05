package com.matji.sandwich.exception;

public class GpsTemporarilyUnavailableMatjiException extends MatjiException {
    public GpsTemporarilyUnavailableMatjiException() {
	setToastMsg("내부 문제로, GPS를 일시적으로 중지합니다. 잠시만 기다려주세요");
    }
}
