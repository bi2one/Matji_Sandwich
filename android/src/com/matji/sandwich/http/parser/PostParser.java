package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.exception.MatjiException;

public class PostParser extends MatjiDataParser<Post> {
	protected Post getMatjiData(JsonObject object) throws MatjiException {
		Post post = new Post();
		post.setId(getInt(object, "id"));
		post.setUserId(getInt(object, "user_id"));
		post.setStoreId(getInt(object, "store_id"));
		post.setPost(getString(object, "post"));
		post.setImageCount(getInt(object, "image_count"));
		post.setLikeCount(getInt(object, "like_count"));
		post.setCommentCount(getInt(object, "comment_count"));
		post.setTagCount(getInt(object, "tag_count"));
		post.setLat(getInt(object, "lat"));
		post.setLng(getInt(object, "lng"));
		post.setFromWhere(getString(object, "from_where"));
		post.setCreatedAt(getString(object, "created_at"));
		post.setUpdatedAt(getString(object, "updated_at"));
		post.setUser(new UserParser().getRawObject(getObject(object, "user") + ""));
		post.setStore(new StoreParser().getRawObject(getObject(object, "store") + ""));
		post.setActivity(new ActivityParser().getRawObject(getObject(object, "activity") + ""));
		
		return post;
	}
}