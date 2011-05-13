package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.exception.MatjiException;

public class FoodParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		Food food = new Food();
		food.setLikeCount(getInt(object, "like_count"));
		food.setCreatedAt(getString(object, "created_at"));
		food.setUpdatedAt(getString(object, "updated_at"));
		food.setId(getInt(object, "id"));
		food.setName(getString(object, "name"));

		return food;
	}
}