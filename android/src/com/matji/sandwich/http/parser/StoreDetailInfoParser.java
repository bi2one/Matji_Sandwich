package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreDetailInfo;
import com.matji.sandwich.exception.MatjiException;

public class StoreDetailInfoParser extends MatjiDataParser {
	public StoreDetailInfoParser(Context context) {
		super(context);
	}

	public StoreDetailInfo getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		StoreDetailInfo info = new StoreDetailInfo();
		info.setId(getInt(object, "id"));
		info.setUserId(getInt(object, "user_id"));
		info.setStoreId(getInt(object, "store_id"));
		info.setCreatedAt(getString(object, "created_at"));
		info.setUpdatedAt(getString(object, "updated_at"));
		info.setNote(getString(object, "note"));
		info.setAgo(getLong(object, "ago"));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser(context);
		info.setStore(storeParser.getMatjiData(getObject(object, "store")));
		
		/* Set User */
		UserParser userParser = new UserParser(context);
		info.setUser(userParser.getMatjiData(getObject(object, "user")));

		Log.d("Parser", "StoreDetailInfoParser:: called getMatjiData");
		
		return info;
	}
}