package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.exception.MatjiException;

public class StoreFoodParser extends MatjiDataParser<StoreFood> {
	protected StoreFood getMatjiData(JsonObject object) throws MatjiException {
		StoreFood storeFood = new StoreFood();
		storeFood.setId(getInt(object, "id"));
		storeFood.setUserId(getInt(object, "user_id"));
		storeFood.setFoodId(getInt(object, "food_id"));
		storeFood.setStoreId(getInt(object, "store_id"));
		storeFood.setLikeCount(getInt(object, "like_count"));
		storeFood.setBlind(getBoolean(object, "blind"));
		storeFood.setStore(new StoreParser().getRawObject(getObject(object, "store") + ""));
		storeFood.setFood(new FoodParser().getRawObject(getObject(object, "food") + ""));
		storeFood.setUser(new UserParser().getRawObject(getObject(object, "user") + ""));
		
		return storeFood;
	}
}