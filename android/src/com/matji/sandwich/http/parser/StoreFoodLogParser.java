package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.StoreFoodLog;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoreFoodLogParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> store_food_logList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		StoreFoodLog store_food_log = new StoreFoodLog();
		store_food_log.setId(element.getInt("id"));
		store_food_log.setStore_id(element.getInt("store_id"));
		store_food_log.setUser_id(element.getInt("user_id"));
		store_food_log.setAction(element.getString("action"));
		store_food_log.setFood_name(element.getString("food_name"));
		store_food_log.setSequence(element.getInt("sequence"));
		store_food_logList.add(store_food_log);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return store_food_logList;
    }
}