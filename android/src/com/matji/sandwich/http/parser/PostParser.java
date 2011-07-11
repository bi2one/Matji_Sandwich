package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.exception.MatjiException;

public class PostParser extends MatjiDataParser {
	public PostParser(Context context) {
		super(context);
	}
	
	protected Post getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Post post = new Post();
		post.setId(getInt(object, "id"));
		post.setUserId(getInt(object, "user_id"));
		post.setStoreId(getInt(object, "store_id"));
		post.setActivityId(getInt(object, "activity_id"));
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
		post.setAgo(getLong(object, "ago"));
		
		/* Set User */
		UserParser userParser = new UserParser(context);
		post.setUser(userParser.getMatjiData(getObject(object, "user")));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser(context);
		post.setStore(storeParser.getMatjiData(getObject(object, "store")));

		/* Set Activity */
		ActivityParser activityParser = new ActivityParser(context);
		post.setActivity(activityParser.getMatjiData(getObject(object, "activity")));		

		/* Set Tags */
		TagParser tagParser = new TagParser(context);
		ArrayList<MatjiData> dataList = tagParser.getMatjiDataList(getArray(object, "tags"));
		ArrayList<Tag> tags = new ArrayList<Tag>(); 
		if (dataList != null) {
			for (MatjiData data : dataList)
				tags.add((Tag) data);
		}
		post.setTags(tags);

		Log.d("Parser", "PostParser:: called getMatjiData");
		
		return post;
	}
}