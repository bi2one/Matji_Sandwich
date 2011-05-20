package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreTag;
import com.matji.sandwich.exception.MatjiException;

public class StoreTagParser extends MatjiDataParser<StoreTag> {
	protected StoreTag getMatjiData(JsonObject object) throws MatjiException {
		StoreTag storeTag = new StoreTag();
		storeTag.setId(getInt(object, "id"));
		storeTag.setTagId(getInt(object, "tag_id"));
		storeTag.setStoreId(getInt(object, "store_id"));
		storeTag.setCount(getInt(object, "count"));
		storeTag.setCreatedAt(getString(object, "created_at"));
		storeTag.setUpdatedAt(getString(object, "updated_at"));
		storeTag.setTag(new TagParser().getRawObject(getObject(object, "tag") + ""));
		storeTag.setStore(new StoreParser().getRawObject(getObject(object, "store") + ""));

		return storeTag;
	}
}