package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.StoreDetailInfoLog;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoreDetailInfoLogParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> store_detail_info_logList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
			try{
			    JSONObject element;
			    for(int i=0 ; i < jsonArray.length() ; i++){
				element = jsonArray.getJSONObject(i);
				StoreDetailInfoLog store_detail_info_log = new StoreDetailInfoLog();
				store_detail_info_log.setId(element.getInt("id"));
				store_detail_info_log.setStore_id(element.getInt("store_id"));
				store_detail_info_log.setUser_id(element.getInt("user_id"));
				store_detail_info_log.setStatus(element.getString("status"));
				store_detail_info_log.setSequence(element.getInt("sequence"));
				store_detail_info_logList.add(store_detail_info_log);
			    }
			} catch(JSONException e){
			    throw new JSONMatjiException();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	return store_detail_info_logList;
    }

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}
}