package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.content.Context;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class StoreParser extends MatjiDataParser {
	public StoreParser(Context context) {
		super(context);
	}

	protected Store getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Store store = new Store();
		store.setId(getInt(object, "id"));
		store.setName(getString(object, "name"));
		store.setRegUserId(getInt(object, "reg_user_id"));
		store.setTel(getString(object, "tel"));
		store.setAddress(getString(object, "address"));
		store.setAddAddress(getString(object, "add_address"));
		store.setWebsite(getString(object, "website"));
		store.setText(getString(object, "text"));
		store.setCover(getString(object, "cover"));
		store.setLat(getDouble(object, "lat"));
		store.setLng(getDouble(object, "lng"));
		store.setTagCount(getInt(object, "tag_count"));
		store.setPostCount(getInt(object, "post_count"));
		store.setImageCount(getInt(object, "image_count"));
		store.setLikeCount(getInt(object, "like_count"));
		store.setBookmarkCount(getInt(object, "bookmark_count"));
		
		/* Set AttachFile */
		AttachFileParser afParser = new AttachFileParser(context);
		AttachFile file = (AttachFile)afParser.getMatjiData(getObject(object, "attach_file"));
		store.setFile(file);
		
		/* Set User */
		UserParser userParser = new UserParser(context);
		User user = (User)userParser.getMatjiData(getObject(object, "user"));
		store.setRegUser(user);

		/* Set Tags */
		TagParser tagParser = new TagParser(context);
		ArrayList<MatjiData> dataList = tagParser.getMatjiDataList(getArray(object, "tags"));
		ArrayList<Tag> tags = new ArrayList<Tag>(); 
		if (dataList != null) {
			for (MatjiData data : dataList)
				tags.add((Tag) data);
		}
		store.setTags(tags);

		/* Set Store Foods */
		StoreFoodParser storeFoodParser = new StoreFoodParser(context);
		dataList = storeFoodParser.getMatjiDataList(getArray(object, "store_foods"));
		ArrayList<StoreFood> storeFoods = new ArrayList<StoreFood>();
		if (dataList != null) {
			for (MatjiData data : dataList)
				storeFoods.add((StoreFood) data);
		}
		store.setStoreFoods(storeFoods);
		
		return store;
	}
}