package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Activity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class PostParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		Post post = new Post();
		post.setId(getInt(object, "id"));
		post.setUserId(getInt(object, "user_id"));
		post.setStoreId(getInt(object, "store_id"));
		post.setPost(getString(object, "post"));
		post.setImageCount(getInt(object, "image_count"));
		post.setLikeCount(getInt(object, "like_count"));
		post.setCommentCount(getInt(object, "comment_count"));
		post.setTagCount(getInt(object, "tag_count"));
		post.setLat(getDouble(object, "lat"));
		post.setLng(getDouble(object, "lng"));
		post.setFromWhere(getString(object, "from_where"));
		post.setCreatedAt(getString(object, "created_at"));
		post.setUpdatedAt(getString(object, "updated_at"));
		post.setUser((User) new UserParser().getRawObject(getString(object, "user")));
		post.setStore((Store) new StoreParser().getRawObject(getString(object, "store")));
		post.setActivity((Activity) new ActivityParser().getRawObject(getString(object, "activity")));
		
		return post;
	}
}