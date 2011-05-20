package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.exception.MatjiException;

public class AttachFileParser extends MatjiDataParser<AttachFile> {
	protected AttachFile getMatjiData(JsonObject object) throws MatjiException {
		AttachFile attachFile = new AttachFile();
		attachFile.setId(getInt(object, "id"));
		attachFile.setUserId(getInt(object, "user_id"));
		attachFile.setStoreId(getInt(object, "store_id"));
		attachFile.setPostId(getInt(object, "post_id"));
		attachFile.setFilename(getString(object, "filename"));
		attachFile.setFullpath(getString(object, "fullpath"));
		attachFile.setUser(new UserParser().getRawObject(getObject(object, "user") + ""));
		attachFile.setStore(new StoreParser().getRawObject(getObject(object, "store") + ""));
		attachFile.setPost(new PostParser().getRawObject(getObject(object, "post") + ""));
		attachFile.setCreatedAt(getString(object, "created_at"));
		attachFile.setUpdatedAt(getString(object, "updated_at"));
		
		return attachFile;		
	}
}