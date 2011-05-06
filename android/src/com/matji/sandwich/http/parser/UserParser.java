package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class UserParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> userList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
			try{
			    JSONObject element;
			    for(int i=0 ; i < jsonArray.length() ; i++){
				element = jsonArray.getJSONObject(i);
				User user = new User();
				user.setId(element.getInt("id"));
				user.setUserid(element.getString("userid"));
				//user.setHashed_password(element.getString("hashed_password"));
				//user.setOld_hashed_password(element.getString("old_hashed_password"));
				//user.setSalt(element.getString("salt"));
				user.setNick(element.getString("nick"));
				//user.setEmail(element.getString("email"));
				user.setTitle(element.getString("title"));
				user.setIntro(element.getString("intro"));
				user.setPost_count(element.getInt("post_count"));
				user.setTag_count(element.getInt("tag_count"));
				user.setStore_count(element.getInt("store_count"));
				user.setFollowing_count(element.getInt("following_count"));
				user.setFollower_count(element.getInt("follower_count"));
				//user.setSequence(element.getInt("sequence"));
				userList.add(user);
				} 
			 }	catch(JSONException e){
				throw new JSONMatjiException();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	return userList;
    }

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}	
}

