package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Region;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.matji.sandwich.data.MatjiData;

public class RegionParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> regionList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
			try{
			    JSONObject element;
			    for(int i=0 ; i < jsonArray.length() ; i++){
				element = jsonArray.getJSONObject(i);
				Region region = new Region();
				region.setId(element.getInt("id"));
				region.setUser_id(element.getInt("user_id"));
				region.setLat_sw(element.getLong("lat_sw"));
				region.setLng_sw(element.getLong("lng_sw"));
				region.setLat_ne(element.getLong("lat_ne"));
				region.setLng_ne(element.getLong("lng_ne"));
				region.setDescription(element.getString("description"));
				region.setSequence(element.getInt("sequence"));
				regionList.add(region);
			    }
			} catch(JSONException e){
			    throw new JSONMatjiException();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	return regionList;
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
	protected MatjiData getRawObject(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MatjiData getMatjiData(JsonObject object) {
		// TODO Auto-generated method stub
		return null;
	}
}