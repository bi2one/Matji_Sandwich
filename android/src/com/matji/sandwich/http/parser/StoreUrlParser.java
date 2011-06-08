package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreUrl;
import com.matji.sandwich.exception.MatjiException;

public class StoreUrlParser extends MatjiDataParser {
	protected StoreUrl getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		StoreUrl storeUrl = new StoreUrl();
		storeUrl.setId(getInt(object, "id"));
		storeUrl.setUserId(getInt(object, "user_id"));
		storeUrl.setStoreId(getInt(object, "store_id"));
		storeUrl.setUrl(getString(object, "url"));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser();
		storeUrl.setStore(storeParser.getMatjiData(getObject(object, "store")));
		
		/* Set User */
		UserParser userParser = new UserParser();
		storeUrl.setUser(userParser.getMatjiData(getObject(object, "user")));

		return storeUrl;
	}
}