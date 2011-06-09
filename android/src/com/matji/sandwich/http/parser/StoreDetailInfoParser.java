package com.matji.sandwich.http.parser;

import android.content.Context;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreDetailInfo;
import com.matji.sandwich.exception.MatjiException;

public class StoreDetailInfoParser extends MatjiDataParser {
	public StoreDetailInfoParser(Context context) {
		super(context);
	}

	protected StoreDetailInfo getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		StoreDetailInfo info = new StoreDetailInfo();
		info.setId(getInt(object, "id"));
		info.setUserId(getInt(object, "user_id"));
		info.setStoreId(getInt(object, "store_id"));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser(context);
		info.setStore(storeParser.getMatjiData(getObject(object, "store")));
		
		/* Set User */
		UserParser userParser = new UserParser(context);
		info.setUser(userParser.getMatjiData(getObject(object, "user")));
		
		return info;
	}
}