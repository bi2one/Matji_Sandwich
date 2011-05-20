package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.UserHttpRequest;

public class StoreParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
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
		store.setFile((AttachFile) new AttachFileParser().getRawObject(getString(object, "file")));
		store.setRegUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));
		
		return store;
	}
}