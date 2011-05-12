package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoreParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> storeList = new ArrayList<MatjiData>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
			try{
			    JSONObject element;
			    for(int i=0 ; i < jsonArray.length() ; i++){
				element = jsonArray.getJSONObject(i);
				Store store = new Store();
				store.setId(element.getString("id"));
				store.setName(element.getString("name"));
				store.setRegUserId(element.getString("reg_user_id"));
				store.setTel(element.getString("tel"));
				store.setAddress(element.getString("address"));
				store.setAddAddress(element.getString("add_address"));
				store.setWebsite(element.getString("website"));
				store.setText(element.getString("cover"));
				store.setLat(element.getLong("lat"));
				store.setLng(element.getLong("lng"));
				store.setTagCount(element.getString("tag_count"));
				store.setPostCount(element.getString("post_count"));
				store.setImageCount(element.getString("image_count"));
				store.setLikeCount(element.getString("like_count"));
				store.setBookmarkCount(element.getString("bookmark_count"));
//				store.setSequence(element.getInt("sequence"));
				storeList.add(store);
			    }
			} catch(JSONException e){
			    throw new JSONMatjiException();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	return storeList;
    }

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}
}