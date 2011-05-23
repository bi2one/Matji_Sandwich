package com.matji.sandwich.data.db.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import android.database.Cursor;

public class SQLiteResult {
	private int code;
	private String error;
	private ArrayList<HashMap<String, String>> result;
	
	public SQLiteResult(Cursor cursor){
		fetch(cursor);
	}
	
	private void fetch(Cursor cursor){
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		String[] columns = cursor.getColumnNames();
		
		cursor.moveToFirst();
        while(!cursor.isAfterLast()){
        	HashMap<String, String> record = new HashMap<String, String>();
        	int index = 0;
        	for(String column : columns){
        		if (!cursor.isNull(index)){
        			try{
        				String value = cursor.getString(index);
        				record.put(column, value);
        			}catch (Exception e){
        				e.printStackTrace();
        				this.code = 0;
        				this.error = "Exception occured during fetch data"; 
        				this.result = null;
        				return;
        			}
        		}        		         		
        		index++;
        	}
        	
        	result.add(record);
        	cursor.moveToNext();
        }

		this.code = 200;	
		this.error = null;
		this.result = result;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getError(){
		return error;
	}
	
	public ArrayList<HashMap<String, String>> getResult(){
		return result;
	}
	
	
}
