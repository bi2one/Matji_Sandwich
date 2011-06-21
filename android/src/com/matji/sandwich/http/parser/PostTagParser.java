package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.PostTag;
import com.matji.sandwich.exception.MatjiException;

public class PostTagParser extends MatjiDataParser {
	public PostTagParser(Context context) {
		super(context);
	}

	protected PostTag getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		PostTag postTag = new PostTag();
		postTag.setId(getInt(object, "id"));
		postTag.setTagId(getInt(object, "tag_id"));
		postTag.setPostId(getInt(object, "post_id"));
		postTag.setCreatedAt(getString(object, "created_at"));
		postTag.setUpdatedAt(getString(object, "updated_at"));
		
		/* Set Tag */
		TagParser tagParser = new TagParser(context);
		postTag.setTag(tagParser.getMatjiData(getObject(object, "tag")));
		
		/* Set Post */
		PostParser postParser = new PostParser(context);
		postTag.setPost(postParser.getMatjiData(getObject(object, "post")));

		Log.d("Parser", "PostTagParser:: called getMatjiData");
		
		return postTag;
	}
}