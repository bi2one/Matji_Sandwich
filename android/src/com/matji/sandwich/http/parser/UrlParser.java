package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Url;
import com.matji.sandwich.exception.MatjiException;

public class UrlParser extends MatjiDataParser {
	public Url getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Url url = new Url();
		url.setId(getInt(object, "id"));
		url.setUserId(getInt(object, "user_id"));
		url.setStoreId(getInt(object, "store_id"));
		url.setUrl(getString(object, "url"));

		return url;
	}
}