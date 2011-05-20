package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.exception.MatjiException;

public class BookmarkParser extends MatjiDataParser<Bookmark> {
	protected Bookmark getMatjiData(JsonObject object) throws MatjiException {
		Bookmark bookmark = new Bookmark();
		bookmark.setId(getInt(object, "id"));
		bookmark.setUserId(getInt(object, "user_id"));
		bookmark.setForeignKey(getInt(object, "foreign_key"));
		bookmark.setObject(getString(object, "object"));
		bookmark.setUser(new UserParser().getRawObject(getObject(object, "user") + ""));
		bookmark.setStore(new StoreParser().getRawObject(getObject(object, "store") + ""));
		bookmark.setRegion(new RegionParser().getRawObject(getObject(object, "region") + ""));
		
		return bookmark;
	}
}