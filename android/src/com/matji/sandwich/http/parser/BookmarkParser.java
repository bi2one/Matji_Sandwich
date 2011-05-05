package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class BookmarkParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> bookmarkList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		Bookmark bookmark = new Bookmark();
		bookmark.setId(element.getInt("id"));
		bookmark.setUser_id(element.getInt("user_id"));
		bookmark.setForeign_key(element.getInt("foreign_key"));
		bookmark.setObject(element.getString("object"));
		bookmark.setSequence(element.getInt("sequence"));
		bookmarkList.add(bookmark);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return bookmarkList;
    }
}