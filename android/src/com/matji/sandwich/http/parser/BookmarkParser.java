package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.exception.MatjiException;

public class BookmarkParser extends MatjiDataParser {
	public BookmarkParser(Context context) {
		super(context);
	}

	protected Bookmark getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Bookmark bookmark = new Bookmark();
		bookmark.setId(getInt(object, "id"));
		bookmark.setUserId(getInt(object, "user_id"));
		bookmark.setForeignKey(getInt(object, "foreign_key"));
		bookmark.setObject(getString(object, "object"));
		
		/* Set User */
		UserParser userParser = new UserParser(context);
		bookmark.setUser(userParser.getMatjiData(getObject(object, "user")));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser(context);
		bookmark.setStore(storeParser.getMatjiData(getObject(object, "store")));
		
		/* Set Region */
		RegionParser regionParser = new RegionParser(context);
		bookmark.setRegion(regionParser.getMatjiData(getObject(object, "region")));

		Log.d("Parser", "BookmarkParser:: called getMatjiData");
		
		return bookmark;
	}
}