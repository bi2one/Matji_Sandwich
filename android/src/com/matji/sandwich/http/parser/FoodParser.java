package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;
import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class FoodParser extends MatjiDataParser{
	public MatjiData getRawObject(String data) throws MatjiException {
		JsonObject jsonObject = null;
		JsonElement jsonElement = null;

		try {			
			JsonParser parser = new JsonParser();
			jsonElement = parser.parse(data);
		} catch (JsonParseException e) {

		}

		if (!isObject(jsonElement)){

		}

		jsonObject = jsonElement.getAsJsonObject();

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

		}

		if (!isArray(jsonElement)) {
			// throw
		}


		jsonArray = jsonElement.getAsJsonArray();


		for (int i = 0; i < jsonArray.size(); i++) {
			foodList.add(getMatjiData(jsonArray.get(i).getAsJsonObject()));
		}

		//Log.d("Matji", "FoodParser: Parsing success");

		return foodList;
	}


	private MatjiData getMatjiData(JsonObject object) {
		object.get("id").getAsInt();
		JsonElement user = object.get("user");


		Food food = new Food();
		food.setLikeCount(element.getString("like_count"));
		food.setCreatedAt(element.getString("created_at"));
		food.setSequence(element.getString("sequence"));
		food.setBlind(element.getString("blind"));
		food.setUpdatedAt(element.getString("updated_at"));
		food.setId(element.getString("id"));
		food.setStoreId(element.getString("store_id"));
		//				TODO
		food.setStore((new StoreParser()).getData(element.getString("store")));
		food.setLike(element.getString("like"));
		food.setUserId(element.getString("user_id"));
		food.setFoodId(element.getString("food_id"));
		food.setName((element.getMatjiJSONObject("food")).getString("name"));
		foodList.add(food);

	}




}