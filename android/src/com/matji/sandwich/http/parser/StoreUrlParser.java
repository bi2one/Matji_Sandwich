package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreUrl;
import com.matji.sandwich.exception.MatjiException;

public class StoreUrlParser extends MatjiDataParser<StoreUrl> {
	protected StoreUrl getMatjiData(JsonObject object) throws MatjiException {
		StoreUrl storeUrl = new StoreUrl();
		storeUrl.setId(getInt(object, "id"));
		storeUrl.setUserId(getInt(object, "user_id"));
		storeUrl.setStoreId(getInt(object, "store_id"));
		storeUrl.setUrl(getString(object, "url"));
		storeUrl.setStore(new StoreParser().getRawObject(getObject(object, "store") + ""));
		storeUrl.setUser(new UserParser().getRawObject(getObject(object, "user") + ""));
		
		return storeUrl;
	}
}