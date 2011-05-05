package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.StoreDetailInfo;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoreDetailInfoParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> store_detail_infoList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		StoreDetailInfo store_detail_info = new StoreDetailInfo();
		store_detail_info.setId(element.getInt("id"));
		store_detail_info.setUser_id(element.getInt("user_id"));
		store_detail_info.setStore_id(element.getInt("store_id"));
		store_detail_info.setNote(element.getString("note"));
		store_detail_info.setSequence(element.getInt("sequence"));
		store_detail_infoList.add(store_detail_info);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return store_detail_infoList;
    }

}