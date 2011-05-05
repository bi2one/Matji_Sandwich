package com.matji.sandwich.exception;

public class GpsServiceNotPossibleMatjiException extends MatjiException {
    public GpsServiceNotPossibleMatjiException() {
	setName("GpsServiceNotPossibleMatjiException");
	setToastMsg("GPS사용이 불가능합니다. 하늘을 볼 수 있거나, 네트워크가 안정적인 곳에서 다시 로딩해 주세요");
    }
}
