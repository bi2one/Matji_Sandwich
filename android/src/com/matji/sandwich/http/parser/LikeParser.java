package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class LikeParser extends MatjiDataParser {
	protected Like getMatjiData(JsonObject object) throws MatjiException {
		Like like = new Like();
		like.setId(getInt(object, "id"));
		like.setUserId(getInt(object, "user_id"));
		like.setForeignKey(getString(object, "foreign_key"));
		like.setObject(getString(object, "object"));
		like.setUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));
		like.setStore((Store) new StoreParser().getRawObject(getObject(object, "store") + ""));
		like.setCreatedAt(getString(object, "created_at"));
		like.setUpdatedAt(getString(object, "updated_at"));
		
		return like;
	}
}