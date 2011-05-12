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
	private String action;
	
	public FollowingParser(String action) {
		this.action = action;
	}
	
	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> followingList = new ArrayList<MatjiData>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
			try{
				JSONObject element;
				for(int i=0 ; i < jsonArray.length() ; i++){
					element = jsonArray.getJSONObject(i);
					Following following = new Following();
					following.setId(element.getInt("id"));
					following.setFollowing_user_id(element.getInt("following_user_id"));
					following.setFollowed_user_id(element.getInt("followed_user_id"));
					followingList.add(following);
					//following.setSequence(element.getInt("sequence"));
				}
			} catch(JSONException e){
				throw new JSONMatjiException();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return followingList;
	}

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}
}