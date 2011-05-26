package com.matji.sandwich.http.parser;

import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Count;
import com.matji.sandwich.exception.MatjiException;

public class CountParser extends MatjiDataParser {
	protected Count getMatjiData(JsonObject object) throws MatjiException {
		Log.d("Matji", "CountParser START");
		if (object == null) return null;
		
		Count count = new Count();
		count.setCount(getInt(object, "count"));

		Log.d("Matji", "CountParser END");
		return count;
	}
}