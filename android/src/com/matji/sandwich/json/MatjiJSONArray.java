package com.matji.sandwich.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.matji.sandwich.exception.JSONMatjiException;

public class MatjiJSONArray extends JSONArray {
	public MatjiJSONArray(String data) throws JSONException {
		super(data);
	}
	
	public JSONObject getJSONObject(int index) throws JSONException {
		try {
			return super.getJSONObject(index);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
