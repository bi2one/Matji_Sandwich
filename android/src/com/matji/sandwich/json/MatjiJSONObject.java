package com.matji.sandwich.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MatjiJSONObject extends JSONObject {
	public JSONArray getJSONArray(String key) throws JSONException {
		try {
			return super.getJSONArray(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	public String getJSONString(String key) throws JSONException {
		try {
			return super.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	public JSONObject getJSONObject(String key) throws JSONException {
		try {
			return super.getJSONObject(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}