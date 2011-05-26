package com.matji.sandwich.http.parser;

import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class AttachFileParser extends MatjiDataParser {
	protected AttachFile getMatjiData(JsonObject object) throws MatjiException {
		Log.d("Matji", "AttachFileParser START");
		if (object == null) return null;
		
		AttachFile attachFile = new AttachFile();
		attachFile.setId(getInt(object, "id"));
		attachFile.setUserId(getInt(object, "user_id"));
		attachFile.setStoreId(getInt(object, "store_id"));
		attachFile.setPostId(getInt(object, "post_id"));
		attachFile.setFilename(getString(object, "filename"));
		attachFile.setFullpath(getString(object, "fullpath"));
		attachFile.setUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));
		attachFile.setStore((Store) new StoreParser().getRawObject(getObject(object, "store") + ""));
		attachFile.setPost((Post) new PostParser().getRawObject(getObject(object, "post") + ""));
		attachFile.setCreatedAt(getString(object, "created_at"));
		attachFile.setUpdatedAt(getString(object, "updated_at"));

		Log.d("Matji", "AttachFileParser END");
		return attachFile;		
	}
}