package com.matji.sandwich.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MatjiJSONObject {
	// TODO 나중에 수정 할 수 있으면 수정
	JSONObject obj;
	
	public MatjiJSONObject(JSONObject obj) {
		this.obj = obj;
	}
	
	public JSONArray getJSONArray(String key) throws JSONException {
		try {
			return obj.getJSONArray(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getString(String key) throws JSONException {
		try {
			return obj.getString(key);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public Long getLong(String key) {
		try {
			return obj.getLong(key);
		} catch (JSONException e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public MatjiJSONObject getMatjiJSONObject(String key) throws JSONException {
		try {
			return new MatjiJSONObject(obj.getJSONObject(key));
		} catch (JSONException e) {
			return null;
		}
	}
}