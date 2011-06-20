package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.exception.MatjiException;

public class LikeParser extends MatjiDataParser {
	public LikeParser(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	protected Like getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Like like = new Like();
		like.setId(getInt(object, "id"));
		like.setUserId(getInt(object, "user_id"));
		like.setForeignKey(getInt(object, "foreign_key"));
		like.setObject(getString(object, "object"));
		
		/* Set User */
		UserParser userParser = new UserParser(context);
		like.setUser(userParser.getMatjiData(getObject(object, "user")));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser(context);
		like.setStore(storeParser.getMatjiData(getObject(object, "store")));

		like.setCreatedAt(getString(object, "created_at"));
		like.setUpdatedAt(getString(object, "updated_at"));

		Log.d("Parser", "LikeParser:: called getMatjiData");
		
		return like;
	}
}