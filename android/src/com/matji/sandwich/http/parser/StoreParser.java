package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;

public class StoreParser extends MatjiDataParser<Store> {
	protected Store getMatjiData(JsonObject object) throws MatjiException {
		Store store = new Store();
		store.setId(getInt(object, "id"));
		store.setName(getString(object, "name"));
		store.setRegUserId(getInt(object, "reg_user_id"));
		store.setTel(getString(object, "tel"));
		store.setAddress(getString(object, "address"));
		store.setAddAddress(getString(object, "add_address"));
		store.setWebsite(getString(object, "website"));
		store.setText(getString(object, "text"));
		store.setCover(getString(object, "cover"));
		store.setLat(getDouble(object, "lat"));
		store.setLng(getDouble(object, "lng"));
		store.setTagCount(getInt(object, "tag_count"));
		store.setPostCount(getInt(object, "post_count"));
		store.setImageCount(getInt(object, "image_count"));
		store.setLikeCount(getInt(object, "like_count"));
		store.setBookmarkCount(getInt(object, "bookmark_count"));
		store.setFile(new AttachFileParser().getRawObject(getString(object, "file")));
		store.setRegUser(new UserParser().getRawObject(getObject(object, "user") + ""));
		store.setTags(new TagParser().getRawObjects(getArray(object, "tags") + ""));
		store.setStoreFoods(new StoreFoodParser().getRawObjects(getArray(object, "store_foods") + ""));
		
		return store;
	}
}