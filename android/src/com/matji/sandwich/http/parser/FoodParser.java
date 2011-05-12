package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;
import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class FoodParser extends MatjiDataParser{
	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> foodList = new ArrayList<MatjiData>();
		MatjiJSONArray jsonArray;
		try {
			jsonArray = new MatjiJSONArray(data);
			MatjiJSONObject element;
			foodList.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				element = jsonArray.getJSONObject(i);
				Food food = new Food();
				food.setBlind(element.getString("blind"));
				food.setCreatedAt(element.getString("created_at"));
				food.setFoodId(element.getString("food_id"));
				food.setId(element.getString("id"));
				food.setLike(element.getString("like"));
				food.setLikeCount(element.getString("like_count"));
				food.setName((element.getMatjiJSONObject("food")).getString("name"));
				food.setSequence(element.getString("sequence"));
				food.setStore((new StoreParser()).getData(element.getString("store")));
				food.setStoreId(element.getString("store_id"));
				food.setUpdatedAt(element.getString("updated_at"));
				food.setUserId(element.getString("user_id"));
				foodList.add(food);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return foodList;
	}

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}
}