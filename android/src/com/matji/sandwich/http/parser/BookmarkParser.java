package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Region;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class BookmarkParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		Bookmark bookmark = new Bookmark();
		bookmark.setId(getInt(object, "id"));
		bookmark.setUserId(getInt(object, "user_id"));
		bookmark.setForeignKey(getInt(object, "foreign_key"));
		bookmark.setObject(getString(object, "object"));
		bookmark.setUser((User) new UserParser().getRawObject(getString(object, "user")));
		bookmark.setStore((Store) new StoreParser().getRawObject(getString(object, "store")));
		bookmark.setRegion((Region) new RegionParser().getRawObject(getString(object, "region")));
		
		return bookmark;
	}
}