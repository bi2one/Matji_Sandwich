package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreDetailInfoLog;
import com.matji.sandwich.exception.MatjiException;

public class StoreDetailInfoLogParser extends MatjiDataParser {
	public StoreDetailInfoLogParser(Context context) {
		super(context);
	}

	public StoreDetailInfoLog getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		StoreDetailInfoLog infoLog = new StoreDetailInfoLog();
		infoLog.setId(getInt(object, "id"));
		infoLog.setStoreId(getInt(object, "store_id"));
		infoLog.setUserId(getInt(object, "user_id"));
		infoLog.setStatus(getString(object, "status"));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser(context);
		infoLog.setStore(storeParser.getMatjiData(getObject(object, "store")));
		
		/* Set User*/
		UserParser userParser = new UserParser(context);
		infoLog.setUser(userParser.getMatjiData(getObject(object, "user")));

		Log.d("Parser", "StoredetailInfoLogParser:: called getMatjiData");
		
		return infoLog;
	}
}