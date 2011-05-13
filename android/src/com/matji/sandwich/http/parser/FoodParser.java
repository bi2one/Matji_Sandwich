package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.exception.MatjiException;

public class FoodParser extends MatjiDataParser{
	public MatjiData getRawObject(String data) throws MatjiException {
		JsonObject jsonObject = null;
		JsonElement jsonElement = null;

		try {			
			JsonParser parser = new JsonParser();
			jsonElement = parser.parse(data);
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new JSONMatjiException();
		}

		if (!isObject(jsonElement)){
			throw new JSONMatjiException();
		}

		jsonObject = jsonElement.getAsJsonObject();

		Log.d("Matji", "FoodParser::getRawObject : Parse success");	
		
		return getMatjiData(jsonObject);
	}

	public ArrayList<MatjiData> getRawObjects(String data) throws MatjiException {
		ArrayList<MatjiData> foodList = new ArrayList<MatjiData>();
		JsonArray jsonArray = null;
		JsonElement jsonElement = null;
		try {			
			JsonParser parser = new JsonParser();
			jsonElement = parser.parse(data);
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new JSONMatjiException();
		}

		if (!isArray(jsonElement)) {
			throw new JSONMatjiException();
		}

		jsonArray = jsonElement.getAsJsonArray();

		for (int i = 0; i < jsonArray.size(); i++) {
			foodList.add(getMatjiData(jsonArray.get(i).getAsJsonObject()));
		}

		Log.d("Matji", "FoodParser::getRawObjects : Parse success");	
		
		return foodList;
	}

	protected MatjiData getMatjiData(JsonObject object) {
		Food food = new Food();
		food.setLikeCount(getInt(object, "like_count"));
		food.setCreatedAt(getString(object, "created_at"));
		food.setUpdatedAt(getString(object, "updated_at"));
		food.setId(getInt(object, "id"));
		food.setName(getString(object, "name"));
		
		return food;
	}
}