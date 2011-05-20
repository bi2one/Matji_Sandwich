package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreDetailInfo;
import com.matji.sandwich.exception.MatjiException;

public class StoreDetailInfoParser extends MatjiDataParser<StoreDetailInfo> {
	protected StoreDetailInfo getMatjiData(JsonObject object) throws MatjiException {
		StoreDetailInfo info = new StoreDetailInfo();
		info.setId(getInt(object, "id"));
		info.setUserId(getInt(object, "user_id"));
		info.setStoreId(getInt(object, "store_id"));
		info.setStore(new StoreParser().getRawObject(getObject(object, "store") + ""));
		info.setUser(new UserParser().getRawObject(getObject(object, "user") + ""));
		return info;
	}
}