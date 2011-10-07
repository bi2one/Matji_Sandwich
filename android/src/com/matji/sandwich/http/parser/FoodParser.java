package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.exception.MatjiException;

public class FoodParser extends MatjiDataParser {
	public Food getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Food food = new Food();
		food.setCreatedAt(getString(object, "created_at"));
		food.setUpdatedAt(getString(object, "updated_at"));
		food.setId(getInt(object, "id"));
		food.setName(getString(object, "name"));

		Log.d("FoodParser", "Parser:: called getMatjiData");
		
		return food;
	}
}