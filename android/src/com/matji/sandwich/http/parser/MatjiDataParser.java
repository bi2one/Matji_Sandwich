package com.matji.sandwich.http.parser;

import android.util.Log;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.exception.JSONCodeMatjiException;
import com.matji.sandwich.http.request.HttpUtility;

import java.util.ArrayList;

import org.json.*;

public abstract class MatjiDataParser {
    public abstract ArrayList<MatjiData> getData(String data) throws MatjiException;
    public abstract ArrayList<MatjiData> getRawData(String data) throws MatjiException;
    
    public String validateData(String data) throws MatjiException{
    	
    	try {
			JSONObject json = new JSONObject(data);
			
			int code = json.getInt("code");
			
			if(code==HttpUtility.HTTP_STATUS_OK){
			    return json.getString("result");
			}else{
				String message = json.getString("description");
				throw new JSONCodeMatjiException(message);
			}	
			
		} catch (JSONException e) {
			throw new JSONMatjiException();
		}
    }
}
