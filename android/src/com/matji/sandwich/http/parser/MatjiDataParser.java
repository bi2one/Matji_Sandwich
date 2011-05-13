package com.matji.sandwich.http.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.exception.JSONCodeMatjiException;
import com.matji.sandwich.http.request.HttpUtility;

import java.util.ArrayList;

import org.json.*;

public abstract class MatjiDataParser {
	protected abstract ArrayList<MatjiData> getRawObjects(String data) throws MatjiException;
	protected abstract MatjiData getRawObject(String data) throws MatjiException;	
	protected abstract MatjiData getMatjiData(JsonObject object);
	
	public String validateData(String data) throws MatjiException{
		try {
			JSONObject json = new JSONObject(data);
			int code = json.getInt("code");
			if (code == HttpUtility.HTTP_STATUS_OK) {
				return json.getString("result");
			}
			else {
				String message = json.getString("description");
				throw new JSONCodeMatjiException(message);
			}
		} catch (JSONException e) {
			throw new JSONMatjiException();
		}
	}
	
	public ArrayList<MatjiData> getObjects(String data) throws MatjiException {
		if (data == null) return null;
		String validData = validateData(data);
		return getRawObjects(validData);
	}
	
	public MatjiData getObject(String data) throws MatjiException{
		if (data == null) return null;
		String validData = validateData(data);
		return getRawObject(validData);		
	}
	protected boolean isNull(JsonElement element) {
		if (element == null || element instanceof JsonNull) {
			return true;
		}
		return false;
	}

	protected boolean isArray(JsonElement element) {
		return (element instanceof JsonArray) ? true : false;

	}

	protected boolean isObject(JsonElement element) {
		return (element instanceof JsonObject) ? true : false;
	}
}
