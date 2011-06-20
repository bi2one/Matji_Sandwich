package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.UserTag;
import com.matji.sandwich.exception.MatjiException;

public class UserTagParser extends MatjiDataParser {
	public UserTagParser(Context context) {
		super(context);
	}
	
	protected UserTag getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		UserTag userTag = new UserTag();
		userTag.setId(getInt(object, "id"));
		userTag.setTagId(getInt(object, "tag_id"));
		userTag.setUserId(getInt(object, "user_id"));
		userTag.setCount(getInt(object, "count"));
		userTag.setCreatedAt(getString(object, "created_at"));
		userTag.setUpdatedAt(getString(object, "updated_at"));

		Log.d("Parser", "UserTagParser:: called getMatjiData");
		
		return userTag;
	}
}