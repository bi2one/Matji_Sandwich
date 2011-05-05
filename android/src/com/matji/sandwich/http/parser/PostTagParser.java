package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.PostTag;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class PostTagParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> post_tagList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		PostTag post_tag = new PostTag();
		post_tag.setId(element.getInt("id"));
		post_tag.setTag_id(element.getInt("tag_id"));
		post_tag.setPost_id(element.getInt("post_id"));
		post_tag.setSequence(element.getInt("sequence"));
		post_tagList.add(post_tag);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return post_tagList;
    }
}