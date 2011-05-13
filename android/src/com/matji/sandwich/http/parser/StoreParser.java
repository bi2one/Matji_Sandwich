package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;
import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class StoreParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> storeList = new ArrayList<MatjiData>();
		MatjiJSONArray jsonArray;
		try {
				jsonArray = new MatjiJSONArray(data);
				MatjiJSONObject element;
				storeList.clear();
				for(int i=0 ; i < jsonArray.length(); i++){
				element = jsonArray.getMatjiJSONObject(i);
				Store store = new Store();
				store.setId(element.getString("id"));
				store.setName(element.getString("name"));
				store.setRegUserId(element.getString("reg_user_id"));
				store.setTel(element.getString("tel"));
				store.setAddress(element.getString("address"));
				store.setAddAddress(element.getString("add_address"));
				store.setWebsite(element.getString("website"));
				store.setText(element.getString("text"));
				store.setCover(element.getString("cover"));
				store.setLat(element.getLong("lat"));
				store.setLng(element.getLong("lng"));
				store.setTagCount(element.getString("tag_count"));
				store.setPostCount(element.getString("post_count"));
				store.setImageCount(element.getString("image_count"));
				store.setLikeCount(element.getString("like_count"));
				store.setBookmarkCount(element.getString("bookmark_count"));
				store.setLike(element.getString("like"));
				store.setBookmark(element.getString("bookmark"));
				store.setNote(element.getString("note"));
				store.setObject(element.getString("object"));
				store.setUser((new UserParser()).parseToMatjiDataList(element.getString("user")));
				storeList.add(store);
			    }
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	return storeList;
    }

	public ArrayList<MatjiData> parseToMatjiDataList(String data) throws MatjiException {
		if (data == null) return null;
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