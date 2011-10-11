package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Count;
import com.matji.sandwich.exception.MatjiException;

public class CountParser extends MatjiDataParser {
	public Count getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Count count = new Count();
		count.setCount(getInt(object, "count"));

		Log.d("CountParser", "Parser:: called getMatjiData");
		
		return count;
	}
}