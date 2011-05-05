package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.StoreTag;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoreTagParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> store_tagList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		StoreTag store_tag = new StoreTag();
		store_tag.setId(element.getInt("id"));
		store_tag.setTag_id(element.getInt("tag_id"));
		store_tag.setStore_id(element.getInt("store_id"));
		store_tag.setCount(element.getInt("count"));
		store_tag.setSequence(element.getInt("sequence"));
		store_tagList.add(store_tag);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return store_tagList;
    }
}