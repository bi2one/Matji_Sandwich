package com.matji.sandwich.http.parser;

import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.data.Region;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class BookmarkParser extends MatjiDataParser {
	protected Bookmark getMatjiData(JsonObject object) throws MatjiException {
		Log.d("Matji", "BookmarkParser START");
		if (object == null) return null;
		
		Bookmark bookmark = new Bookmark();
		bookmark.setId(getInt(object, "id"));
		bookmark.setUserId(getInt(object, "user_id"));
		bookmark.setForeignKey(getInt(object, "foreign_key"));
		bookmark.setObject(getString(object, "object"));
		bookmark.setUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));
		bookmark.setStore((Store) new StoreParser().getRawObject(getObject(object, "store") + ""));
		bookmark.setRegion((Region) new RegionParser().getRawObject(getObject(object, "region") + ""));
		
		Log.d("Matji", "BookmarkParser END");
		return bookmark;
	}
}