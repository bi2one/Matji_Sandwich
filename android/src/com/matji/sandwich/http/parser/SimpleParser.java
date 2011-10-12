package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Simple;
import com.matji.sandwich.exception.MatjiException;

public class SimpleParser extends MatjiDataParser {
	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		Simple simpleData = new Simple();
	 	ArrayList<MatjiData> list = new ArrayList<MatjiData>();
		
		simpleData.setContent(data);
		list.add(simpleData);
		return list; 
	}

	public ArrayList<MatjiData> parseToMatjiDataList(String data) throws MatjiException {
    	String validData = validateData(data);
    	return getRawData(validData);
    }

	@Override
	protected ArrayList<MatjiData> getRawObjects(String data)
			throws MatjiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MatjiData getRawObject(String data) throws MatjiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MatjiData getMatjiData(JsonObject object) {
		// TODO Auto-generated method stub
		return null;
	}
}