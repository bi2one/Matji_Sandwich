package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoreFoodParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> store_foodList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		StoreFood store_food = new StoreFood();
		store_food.setId(element.getInt("id"));
		store_food.setUser_id(element.getInt("user_id"));
		store_food.setFood_id(element.getInt("food_id"));
		store_food.setStore_id(element.getInt("store_id"));
		store_food.setLike_count(element.getInt("like_count"));
		store_food.setBlind(element.getInt("blind"));
		store_food.setSequence(element.getInt("sequence"));
		store_foodList.add(store_food);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return store_foodList;
    }
}