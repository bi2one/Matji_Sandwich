package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.exception.MatjiException;

public class LikeParser extends MatjiDataParser {


	public Like getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Like like = new Like();
		like.setId(getInt(object, "id"));
		like.setUserId(getInt(object, "user_id"));
		like.setForeignKey(getInt(object, "foreign_key"));
		like.setObject(getString(object, "object"));
		
		/* Set User */
		UserParser userParser = new UserParser();
		like.setUser(userParser.getMatjiData(getObject(object, "user")));
		
		/* Set Store */
		StoreParser storeParser = new StoreParser();
		like.setStore(storeParser.getMatjiData(getObject(object, "store")));

		like.setCreatedAt(getString(object, "created_at"));
		like.setUpdatedAt(getString(object, "updated_at"));

		return like;
	}
}