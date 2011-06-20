package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreUrl;
import com.matji.sandwich.exception.MatjiException;

public class StoreUrlParser extends MatjiDataParser {
	public StoreUrlParser(Context context) {
		super(context);
	}

	protected StoreUrl getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		StoreUrl storeUrl = new StoreUrl();
		storeUrl.setId(getInt(object, "id"));
		storeUrl.setUserId(getInt(object, "user_id"));
		storeUrl.setStoreId(getInt(object, "store_id"));
		storeUrl.setUrl(getString(object, "url"));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser(context);
		storeUrl.setStore(storeParser.getMatjiData(getObject(object, "store")));
		
		/* Set User */
		UserParser userParser = new UserParser(context);
		storeUrl.setUser(userParser.getMatjiData(getObject(object, "user")));

		Log.d("Parser", "StoreUrlParser:: called getMatjiData");
		
		return storeUrl;
	}
}