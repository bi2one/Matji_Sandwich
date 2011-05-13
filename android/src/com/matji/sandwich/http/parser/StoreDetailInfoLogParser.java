package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreDetailInfoLog;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class StoreDetailInfoLogParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		StoreDetailInfoLog infoLog = new StoreDetailInfoLog();
		infoLog.setId(getInt(object, "id"));
		infoLog.setStoreId(getInt(object, "store_id"));
		infoLog.setUserId(getInt(object, "user_id"));
		infoLog.setStatus(getString(object, "status"));
		infoLog.setStore((Store) new StoreParser().getRawObject(getObject(object, "store") + ""));
		infoLog.setUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));

		return infoLog;
	}
}