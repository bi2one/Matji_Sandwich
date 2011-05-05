package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.StoreUrl;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoreUrlParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> store_urlList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		StoreUrl store_url = new StoreUrl();
		store_url.setId(element.getInt("id"));
		store_url.setUser_id(element.getInt("user_id"));
		store_url.setStore_id(element.getInt("store_id"));
		store_url.setUrl(element.getString("url"));
		store_url.setSequence(element.getInt("sequence"));
		store_urlList.add(store_url);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return store_urlList;
    }
}