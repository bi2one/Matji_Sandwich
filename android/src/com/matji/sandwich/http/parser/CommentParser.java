package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.exception.MatjiException;

public class CommentParser extends MatjiDataParser {
	public Comment getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Comment comment = new Comment();
		comment.setComment(getString(object, "comment"));
		comment.setCreatedAt(getString(object, "created_at"));
		comment.setUpdatedAt(getString(object, "updated_at"));
		comment.setPostId(getInt(object, "post_id"));
		comment.setId(getInt(object, "id"));
		comment.setUserId(getInt(object, "user_id"));
		comment.setAgo(getLong(object, "ago"));
		
		/* Set User */
		UserParser userParser = new UserParser();
		comment.setUser(userParser.getMatjiData(getObject(object, "user")));
		
		/* Set Post */
		PostParser postParser = new PostParser();
		comment.setPost(postParser.getMatjiData(getObject(object, "post")));
		comment.setFromWhere(getString(object, "from_where"));

		return comment;
	}
}