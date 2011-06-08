package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.PostTag;
import com.matji.sandwich.exception.MatjiException;

public class PostTagParser extends MatjiDataParser {
	protected PostTag getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		PostTag postTag = new PostTag();
		postTag.setId(getInt(object, "id"));
		postTag.setTagId(getInt(object, "tag_id"));
		postTag.setPostId(getInt(object, "post_id"));
		postTag.setCreatedAt(getString(object, "created_at"));
		postTag.setUpdatedAt(getString(object, "updated_at"));
		
		/* Set Tag */
		TagParser tagParser = new TagParser();
		postTag.setTag(tagParser.getMatjiData(getObject(object, "tag")));
		
		/* Set Post */
		PostParser postParser = new PostParser();
		postTag.setPost(postParser.getMatjiData(getObject(object, "post")));

		return postTag;
	}
}