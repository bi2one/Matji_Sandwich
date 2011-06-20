package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.exception.MatjiException;

public class TagParser extends MatjiDataParser {
	public TagParser(Context context) {
		super(context);
	}

	protected Tag getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Tag tag = new Tag();
		tag.setTag(getString(object, "tag"));
		tag.setCreatedAt(getString(object, "created_at"));
		tag.setUpdatedAt(getString(object, "updated_at"));
		tag.setId(getInt(object, "id"));

		Log.d("Parser", "TagParser:: called getMatjiData");
		
		return tag;
	}
}