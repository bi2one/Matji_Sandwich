package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.exception.MatjiException;

public class StoreFoodParser extends MatjiDataParser {
	public StoreFoodParser(Context context) {
		super(context);	}

	protected StoreFood getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;

		StoreFood storeFood = new StoreFood();
		storeFood.setId(getInt(object, "id"));
		storeFood.setUserId(getInt(object, "user_id"));
		storeFood.setFoodId(getInt(object, "food_id"));
		storeFood.setStoreId(getInt(object, "store_id"));
		storeFood.setLikeCount(getInt(object, "like_count"));
		storeFood.setBlind(getBoolean(object, "blind"));

		/* Set Store */
		StoreParser storeParser = new StoreParser(context);
		storeFood.setStore(storeParser.getMatjiData(getObject(object, "store")));

		/* Set Food */
		FoodParser foodParser = new FoodParser(context);
		storeFood.setFood(foodParser.getMatjiData(getObject(object, "food")));

		/* Set User */
		UserParser userParser = new UserParser(context);
		storeFood.setUser(userParser.getMatjiData(getObject(object, "user")));

		Log.d("Parser", "StoreFoodParser:: called getMatjiData");
		
		return storeFood;
	}
}