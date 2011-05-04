package com.matji.sandwich.http.parser;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Simple;

import java.util.ArrayList;

public class SimpleParser extends MatjiDataParser {
    public ArrayList<MatjiData> getData(String data) {
	Simple simpleData = new Simple();
 	ArrayList<MatjiData> list = new ArrayList<MatjiData>();
	
	simpleData.setContent(data);
	list.add(simpleData);
	return list;
    }
}