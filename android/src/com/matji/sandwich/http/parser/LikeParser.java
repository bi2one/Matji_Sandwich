package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class LikeParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> likeList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
	try {
	    jsonArray = new JSONArray(data);
	    try{
		JSONObject element;
		for(int i=0 ; i < jsonArray.length() ; i++){
		    element = jsonArray.getJSONObject(i);
		    Like like = new Like();
//		    like.setId(element.getInt("id"));
//		    like.setUser_id(element.getInt("user_id"));
//		    like.setForeign_key(element.getInt("foreign_key"));
//		    like.setObject(element.getString("object"));
//		    like.setSequence(element.getInt("sequence"));
		    likeList.add(like);
		}
	    } catch(JSONException e){
		throw new JSONMatjiException();
	    }
	} catch (JSONException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return likeList;
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