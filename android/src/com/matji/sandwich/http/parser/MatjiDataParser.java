package com.matji.sandwich.http.parser;

import com.matji.sandwich.data.MatjiData;

import com.matji.sandwich.exception.MatjiException;

import com.matji.sandwich.exception.JSONMatjiException;

import com.matji.sandwich.exception.JSONCodeMatjiException;

import java.util.ArrayList;

import org.json.*;

public abstract class MatjiDataParser {
    public abstract ArrayList<MatjiData> getData(String data) throws MatjiException;
    
    public JSONArray validateData(String data) throws MatjiException{
    	
    	try {
			JSONObject json = new JSONObject(data);
			
			int code = json.getInt("code");
			
			if(code==200){
		    	return json.getJSONArray("result");
			}else{
				String message = json.getString("message");
				throw new JSONCodeMatjiException(message);
			}	
			
		} catch (JSONException e) {
			throw new JSONMatjiException();
		}
    }
}
