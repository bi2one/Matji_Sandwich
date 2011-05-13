package com.matji.sandwich.json;

import org.json.JSONArray;
import org.json.JSONException;

import com.matji.sandwich.exception.JSONMatjiException;

public class MatjiJSONArray extends JSONArray {

	// TODO 나중에 수정 할 수 있으면 수정
	public MatjiJSONArray(String data) throws JSONException {
		super(data);
	}
	
	public MatjiJSONObject getMatjiJSONObject(int index) {
		try {
			return new MatjiJSONObject(super.getJSONObject(index));
		} catch (JSONException e) {
			return null;
		}
	}
}