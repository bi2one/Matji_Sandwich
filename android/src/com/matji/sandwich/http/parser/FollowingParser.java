package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Following;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;
import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class FollowingParser extends MatjiDataParser{	
	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> followingList = new ArrayList<MatjiData>();
		MatjiJSONArray jsonArray;
		try {
			jsonArray = new MatjiJSONArray(data);
			MatjiJSONObject element;
			for(int i=0 ; i < jsonArray.length() ; i++){
				element = jsonArray.getMatjiJSONObject(i);
				Following following = new Following();
				following.setCreatedAt(element.getString("created_at"));
				following.setFollowedUserId(element.getString("followed_user_id"));
				following.setFollowingUserId(element.getString("following_user_id"));
				following.setId(element.getString("id"));
				following.setSequence(element.getString("sequence"));
				following.setUpdatedAt(element.getString("updated_at"));
				followingList.add(following);
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