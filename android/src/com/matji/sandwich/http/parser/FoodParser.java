package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.util.Log;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class FoodParser extends MatjiDataParser{
	private String action;

	public FoodParser(String action) {
		this.action = action;
	}

	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> foodList = new ArrayList<MatjiData>();
		JSONArray jsonArray;
		Log.d("Matji", action);
		try {
			jsonArray = new JSONArray(data);
			try{
				JSONObject element;
				if (action.equals("new")) {
					foodList.clear();
					Log.d("Matji", ""+jsonArray.length());
					for (int i = 0; i < jsonArray.length(); i++) {
						element = jsonArray.getJSONObject(i);
						Food food = new Food();
						food.setId(element.getInt("id"));
						food.setName((element.getJSONObject("food")).getString("name"));
						food.setLike_count(element.getInt("like_count"));
						foodList.add(food);
					}
				}
				else if (action.equals("delete")) {}
				else if (action.equals("like")) {}
				else if (action.equals("list")){
					foodList.clear();
					for (int i=0 ; i < jsonArray.length(); i++){
						element = jsonArray.getJSONObject(i);
						Food food = new Food();
						food.setId(element.getInt("id"));
						food.setName((element.getJSONObject("food")).getString("name"));
						food.setLike_count(element.getInt("like_count"));
						foodList.add(food);
					}
				}
				else { /* ACTION ERROR ! */ }
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