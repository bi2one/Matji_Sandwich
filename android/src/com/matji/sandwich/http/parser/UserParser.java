package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;
import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class UserParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> userList = new ArrayList<MatjiData>();
    	MatjiJSONArray jsonArray;
	try {
	    jsonArray = new MatjiJSONArray(data);
		userList.clear();
	    MatjiJSONObject element;
		for(int i=0 ; i < jsonArray.length() ; i++){
		    element = jsonArray.getMatjiJSONObject(i);
		    User user = new User();
		    user.setId(element.getString("id"));
		    user.setUserid(element.getString("userid"));
		    user.setNick(element.getString("nick"));
		    user.setTitle(element.getString("title"));
		    user.setIntro(element.getString("intro"));
		    user.setPostCount(element.getString("post_count"));
		    user.setTagCount(element.getString("tag_count"));
		    user.setStoreCount(element.getString("store_count"));
		    user.setFollowingCount(element.getString("following_count"));
		    user.setFollowerCount(element.getString("follower_count"));
		    //user.setSequence(element.getInt("sequence"));
		    userList.add(user);
		} 
	} catch (JSONException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return userList;
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

