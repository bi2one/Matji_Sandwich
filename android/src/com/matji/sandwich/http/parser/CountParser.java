package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Count;
import com.matji.sandwich.exception.MatjiException;

public class CountParser extends MatjiDataParser {
	protected Count getMatjiData(JsonObject object) throws MatjiException {
		Count count = new Count();
		count.setCount(getInt(object, "count"));
		
		return count;
	}
}