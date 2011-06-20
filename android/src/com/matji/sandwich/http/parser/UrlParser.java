package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Url;
import com.matji.sandwich.exception.MatjiException;

public class UrlParser extends MatjiDataParser {
	public UrlParser(Context context) {
		super(context);
	}
	
	protected Url getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Url url = new Url();
		url.setId(getInt(object, "id"));
		url.setUserId(getInt(object, "user_id"));
		url.setStoreId(getInt(object, "store_id"));
		url.setUrl(getString(object, "url"));

		Log.d("Parser", "UrlParser:: called getMatjiData");
		
		return url;
	}
}