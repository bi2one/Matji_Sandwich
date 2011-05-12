package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.util.Log;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class FoodParser extends MatjiDataParser{
	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> foodList = new ArrayList<MatjiData>();
		MatjiJSONArray jsonArray;
		try {
			jsonArray = new MatjiJSONArray(data);
			try{
				MatjiJSONObject element;
				for (int i = 0; i < jsonArray.length(); i++) {
					element = jsonArray.getJSONObject(i);
					foodList.clear();
					Food food = new Food();
					food.setId(element.getString("id"));
					food.setName((element.getJSONObject("food")).getString("name"));
//					food.setLike_count(element.getString("like_count"));
					foodList.add(food);
				}
			} catch(JSONException e){
				e.printStackTrace();
				throw new JSONMatjiException();
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