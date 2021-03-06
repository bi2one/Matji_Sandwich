package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.exception.MatjiException;

public class PostParser extends MatjiDataParser {
	public Post getMatjiData(JsonObject object) throws MatjiException {
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
		UserParser userParser = new UserParser();
		post.setUser(userParser.getMatjiData(getObject(object, "user")));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser();
		post.setStore(storeParser.getMatjiData(getObject(object, "store")));

		/* Set Activity */
		ActivityParser activityParser = new ActivityParser();
		post.setActivity(activityParser.getMatjiData(getObject(object, "activity")));		

		/* Set Tags */
		TagParser tagParser = new TagParser();
		ArrayList<MatjiData> dataList = tagParser.getMatjiDataList(getArray(object, "tags"));
		ArrayList<SimpleTag> tags = new ArrayList<SimpleTag>(); 
		if (dataList != null) {
			for (MatjiData data : dataList)
				tags.add((SimpleTag) data);
		}
		post.setTags(tags);
		
		/* Set AttachFiles */
		AttachFileParser attachFileParser = new AttachFileParser();
		dataList = attachFileParser.getMatjiDataList(getArray(object, "attach_files"));
		ArrayList<AttachFile> attach_files = new ArrayList<AttachFile>(); 
		if (dataList != null) {
			for (MatjiData data : dataList)
				attach_files.add((AttachFile) data);
		}
		post.setAttachFiles(attach_files);

		return post;
	}
}