package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.exception.MatjiException;

public class AttachFileParser extends MatjiDataParser {
	public AttachFile getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		AttachFile attachFile = new AttachFile();
		attachFile.setId(getInt(object, "id"));
		attachFile.setUserId(getInt(object, "user_id"));
		attachFile.setStoreId(getInt(object, "store_id"));
		attachFile.setPostId(getInt(object, "post_id"));
		attachFile.setFilename(getString(object, "filename"));
		attachFile.setFullpath(getString(object, "fullpath"));
		
		/* Set User */
		UserParser userParser = new UserParser();
		attachFile.setUser(userParser.getMatjiData(getObject(object, "user")));
		
		/* Set Attach File */
		StoreParser storeParser = new StoreParser();
		attachFile.setStore(storeParser.getMatjiData(getObject(object, "store")));
		
		/* Set Post */
		PostParser postParser = new PostParser();
		attachFile.setPost(postParser.getMatjiData(getObject(object, "post")));
		
		attachFile.setCreatedAt(getString(object, "created_at"));
		attachFile.setUpdatedAt(getString(object, "updated_at"));
		
		Log.d("AttachFileParser", "Parser:: called getMatjiData");
		
		return attachFile;
	}
}