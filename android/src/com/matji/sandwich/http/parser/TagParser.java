package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class TagParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> tagList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		Tag tag = new Tag();
		tag.setId(element.getInt("id"));
		tag.setTag(element.getString("tag"));
		tag.setSequence(element.getInt("sequence"));
		tagList.add(tag);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return tagList;
    }
}