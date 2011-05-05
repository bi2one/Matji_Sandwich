package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.UserMileage;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class UserMileageParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> user_mileageList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		UserMileage user_mileage = new UserMileage();
		user_mileage.setId(element.getInt("id"));
		user_mileage.setUser_id(element.getInt("user_id"));
		user_mileage.setTotal_point(element.getInt("total_point"));
		user_mileage.setGrade(element.getString("grate"));
		user_mileage.setSpecial_user(element.getInt("special_user"));
		user_mileage.setBlacklist_user(element.getInt("blacklist_user"));
		user_mileage.setSequence(element.getInt("sequence"));
		user_mileageList.add(user_mileage);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return user_mileageList;
    }
}