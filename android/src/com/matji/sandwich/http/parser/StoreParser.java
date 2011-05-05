package com.matji.sandwich.http.parser;

import java.util.ArrayList;
import android.util.Log;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class StoreParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
			ArrayList<MatjiData> storeList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		Store store = new Store();
		store.setId(element.getInt("id"));
		store.setName(element.getString("name"));
		store.setReg_user_id(element.getInt("reg_user_id"));
		store.setTel(element.getString("tel"));
		store.setAddress(element.getString("address"));
		store.setAdd_address(element.getString("add_address"));
		store.setWebsite(element.getString("website"));
		store.setText(element.getString("cover"));
		store.setLat(element.getLong("lat"));
		store.setLng(element.getLong("lng"));
		store.setTag_count(element.getInt("tag_count"));
		store.setPost_count(element.getInt("post_count"));
		store.setImage_count(element.getInt("image_count"));
		store.setLike_count(element.getInt("like_count"));
		store.setBookmark_count(element.getInt("bookmark_count"));
//		store.setSequence(element.getInt("sequence"));
		storeList.add(store);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return storeList;
    }
}