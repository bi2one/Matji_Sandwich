package com.matji.sandwich.http.parser;

import java.util.ArrayList;
import com.matji.sandwich.data.Region;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.matji.sandwich.data.MatjiData;

public class RegionParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> regionList = new ArrayList<MatjiData>();

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
	return regionList;
    }
}