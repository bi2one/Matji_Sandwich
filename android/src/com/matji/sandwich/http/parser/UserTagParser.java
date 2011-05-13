package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.UserTag;
import com.matji.sandwich.exception.MatjiException;

public class UserTagParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		UserTag userTag = new UserTag();
		userTag.setId(getInt(object, "id"));
		userTag.setTagId(getInt(object, "tag_id"));
		userTag.setUserId(getInt(object, "user_id"));
		userTag.setCount(getInt(object, "count"));
		userTag.setCreatedAt(getString(object, "created_at"));
		userTag.setUpdatedAt(getString(object, "updated_at"));

		return userTag;
	}
}