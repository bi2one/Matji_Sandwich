package com.matji.sandwich.http.parser;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Simple;
import com.matji.sandwich.exception.*;

import java.util.ArrayList;

public class SimpleParser extends MatjiDataParser {
	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		Simple simpleData = new Simple();
	 	ArrayList<MatjiData> list = new ArrayList<MatjiData>();
		
		simpleData.setContent(data);
		list.add(simpleData);
		return list; 
	}

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
    	String validData = validateData(data);
    	return getRawData(validData);
    }
}