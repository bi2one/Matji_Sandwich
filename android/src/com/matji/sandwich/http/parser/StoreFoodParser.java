package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class StoreFoodParser extends MatjiDataParser {
	protected StoreFood getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		StoreFood storeFood = new StoreFood();
		storeFood.setId(getInt(object, "id"));
		storeFood.setUserId(getInt(object, "user_id"));
		storeFood.setFoodId(getInt(object, "food_id"));
		storeFood.setStoreId(getInt(object, "store_id"));
		storeFood.setLikeCount(getInt(object, "like_count"));
		storeFood.setBlind(getBoolean(object, "blind"));
		storeFood.setStore((Store) new StoreParser().getRawObject(getObject(object, "store") + ""));
		storeFood.setFood((Food) new FoodParser().getRawObject(getObject(object, "food") + ""));
		storeFood.setUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));
		
		return storeFood;
	}
}