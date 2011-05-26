package com.matji.sandwich.http.parser;

import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreTag;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.exception.MatjiException;

public class StoreTagParser extends MatjiDataParser {
	protected StoreTag getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		StoreTag storeTag = new StoreTag();
		storeTag.setId(getInt(object, "id"));
		storeTag.setTagId(getInt(object, "tag_id"));
		storeTag.setStoreId(getInt(object, "store_id"));
		storeTag.setCount(getInt(object, "count"));
		storeTag.setCreatedAt(getString(object, "created_at"));
		storeTag.setUpdatedAt(getString(object, "updated_at"));
		storeTag.setTag((Tag) new TagParser().getRawObject(getObject(object, "tag") + ""));
		storeTag.setStore((Store) new StoreParser().getRawObject(getObject(object, "store") + ""));

		Log.d("Matji", "StoreTagParser END");
		return storeTag;
	}
}