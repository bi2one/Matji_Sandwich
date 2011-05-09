package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Activity;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class ActivityParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> activityList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
	try {
	    jsonArray = new JSONArray(data);
	    try{
		JSONObject element;
		for(int i=0 ; i < jsonArray.length() ; i++){
		    element = jsonArray.getJSONObject(i);
		    Activity activity = new Activity();
		    activity.setId(element.getInt("id"));
		    activity.setUser_name(element.getString("user_name"));
		    activity.setUser_id(element.getString("user_id"));
		    activity.setObject_type(element.getString("object_type"));
		    activity.setObject_name(element.getString("object_name"));
		    activity.setObject_id(element.getString("object_id"));
		    activity.setObject_complement_type(element.getString("object_complement_type"));
		    activity.setObject_complement_name(element.getString("object_complement_name"));
		    activity.setObject_complement_id(element.getString("objet_complement_id"));
		    activity.setAction(element.getString("action"));
		    activity.setSequence(element.getInt("sequence"));
		    activityList.add(activity);
		}
	    } catch(JSONException e){
		throw new JSONMatjiException();
	    }
	} catch (JSONException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return activityList;
    }

    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	String validData = validateData(data);
	return getRawData(validData);
    }

}