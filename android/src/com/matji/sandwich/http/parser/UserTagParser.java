package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.UserTag;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class UserTagParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> user_tagList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		UserTag user_tag = new UserTag();
		user_tag.setId(element.getInt("id"));
		user_tag.setTag_id(element.getInt("tag_id"));
		user_tag.setUser_id(element.getInt("user_id"));
		user_tag.setCount(element.getInt("count"));
		user_tag.setSequence(element.getInt("sequence"));
		user_tagList.add(user_tag);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return user_tagList;
    }
}