package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreUrl;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class StoreUrlParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		StoreUrl storeUrl = new StoreUrl();
		storeUrl.setId(getInt(object, "id"));
		storeUrl.setUserId(getInt(object, "user_id"));
		storeUrl.setStoreId(getInt(object, "store_id"));
		storeUrl.setUrl(getString(object, "url"));
		storeUrl.setStore((Store) new StoreParser().getRawObject(getObject(object, "store") + ""));
		storeUrl.setUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));
		
		return storeUrl;
	}
}