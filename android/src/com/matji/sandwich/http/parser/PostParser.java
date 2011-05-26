package com.matji.sandwich.http.parser;

import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Activity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class PostParser extends MatjiDataParser {
	protected Post getMatjiData(JsonObject object) throws MatjiException {
		Log.d("Matji", "PostParser START");
		if (object == null) return null;
		
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
		post.setUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));
		post.setStore((Store) new StoreParser().getRawObject(getObject(object, "store") + ""));
		post.setActivity((Activity) new ActivityParser().getRawObject(getObject(object, "activity") + ""));
		
		Log.d("Matji", "PostParser END");
		return post;
	}
}