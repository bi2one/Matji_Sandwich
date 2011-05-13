package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.StoreUrl;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoreUrlParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> store_urlList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
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
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	return store_urlList;
    }

	public ArrayList<MatjiData> parseToMatjiDataList(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}

	@Override
	protected ArrayList<MatjiData> getRawObjects(String data)
			throws MatjiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MatjiData getRawObject(String data) throws MatjiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MatjiData getMatjiData(JsonObject object) {
		// TODO Auto-generated method stub
		return null;
	}
}