package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreTag;
import com.matji.sandwich.exception.MatjiException;

public class StoreTagParser extends MatjiDataParser {
	public StoreTag getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		StoreTag storeTag = new StoreTag();
		storeTag.setId(getInt(object, "id"));
		storeTag.setTagId(getInt(object, "tag_id"));
		storeTag.setStoreId(getInt(object, "store_id"));
		storeTag.setCount(getInt(object, "count"));
		storeTag.setCreatedAt(getString(object, "created_at"));
		storeTag.setUpdatedAt(getString(object, "updated_at"));
		
		/* Set Tag */
		TagParser tagParser = new TagParser();
		storeTag.setTag(tagParser.getMatjiData(getObject(object, "tag")));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser();
		storeTag.setStore(storeParser.getMatjiData(getObject(object, "store")));

		Log.d("Parser", "StoreTagParser:: called getMatjiData");
		
		return storeTag;
	}
}