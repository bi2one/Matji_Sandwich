package com.matji.sandwich.data.db.parser;

import java.util.ArrayList;
import java.util.HashMap;

import com.matji.sandwich.data.db.sqlite.SQLiteResult;

public abstract class DBParser<T> {
	public abstract ArrayList<T> parse(SQLiteResult sqliteResult);
	
	protected int getInt(HashMap<String, String> record, String key){
		String value = record.get(key);
		return (value != null) ? Integer.parseInt(value) : 0;
	}
	
	protected double getDouble(HashMap<String, String> record, String key){
		String value = record.get(key);
		return (value != null) ? Double.parseDouble(value) : 0;
	}
	
	protected String getString(HashMap<String, String> record, String key){
		return record.get(key);
	}
	
	
}
