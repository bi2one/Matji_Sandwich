package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Following;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class FollowingParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> FollowingList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		Following following = new Following();
		following.setId(element.getInt("id"));
		following.setFollowing_user_id(element.getInt("following_user_id"));
		following.setFollowed_user_id(element.getInt("followed_user_id"));
		following.setSequence(element.getInt("sequence"));
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return FollowingList;
    }
}