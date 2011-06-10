package com.matji.sandwich.http.parser;

import android.content.Context;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Count;
import com.matji.sandwich.exception.MatjiException;

public class CountParser extends MatjiDataParser {
	public CountParser(Context context) {
		super(context);
	}

	protected Count getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Count count = new Count();
		count.setCount(getInt(object, "count"));

		return count;
	}
}