package com.matji.sandwich.exception;

public class JSONMatjiException  extends MatjiException {
	public JSONMatjiException(){
		setToastMsg("JSON Parsing Error");	
	}
}
