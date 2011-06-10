package com.matji.sandwich.http.parser;

import android.content.Context;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Activity;
import com.matji.sandwich.exception.MatjiException;

public class ActivityParser extends MatjiDataParser {
	public ActivityParser(Context context) {
		super(context);
	}

	protected Activity getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Activity activity = new Activity();
		activity.setId(getInt(object, "id"));
		activity.setUserName(getString(object, "user_name"));
		activity.setUserId(getInt(object, "user_id"));
		activity.setObjectType(getString(object, "object_type"));
		activity.setObjectName(getString(object, "object_name"));
		activity.setObjectId(getInt(object, "object_id"));
		activity.setObjectComplementType(getString(object, "object_complement_type"));
		activity.setObjectComplementName(getString(object, "object_complement_name"));
		activity.setObjectComplementId(getInt(object, "object_complement_id"));
		
		return activity;
	}
}