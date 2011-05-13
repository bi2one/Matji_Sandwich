package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreDetailInfo;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class StoreDetailInfoParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		StoreDetailInfo info = new StoreDetailInfo();
		info.setId(getInt(object, "id"));
		info.setUserId(getInt(object, "user_id"));
		info.setStoreId(getInt(object, "store_id"));
		info.setStore((Store) new StoreParser().getRawObject(getString(object, "store")));
		info.setUser((User) new UserParser().getRawObject(getString(object, "user")));
		return info;
	}
}