package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class FoodParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> foodList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		Food food = new Food();
		food.setId(element.getInt("id"));
		food.setName(element.getString("name"));
		food.setLike_count(element.getInt("like_count"));
		food.setSequence(element.getInt("sequence"));
		foodList.add(food);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return foodList;
    }
}