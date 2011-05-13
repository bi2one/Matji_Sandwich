package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class StoreFoodParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		StoreFood storeFood = new StoreFood();
		storeFood.setId(getInt(object, "id"));
		storeFood.setUserId(getInt(object, "user_id"));
		storeFood.setFoodId(getInt(object, "food_id"));
		storeFood.setStoreId(getInt(object, "store_id"));
		storeFood.setLikeCount(getInt(object, "like_count"));
		storeFood.setBlind(getBoolean(object, "blind"));
		storeFood.setStore((Store) new StoreParser().getRawObject(getString(object, "store")));
		storeFood.setFood((Food) new FoodParser().getRawObject(getString(object, "food")));
		storeFood.setUser((User) new UserParser().getRawObject(getString(object, "user")));
		
		return storeFood;
	}
}