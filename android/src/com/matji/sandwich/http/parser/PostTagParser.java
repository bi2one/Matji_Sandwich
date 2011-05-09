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
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> post_tagList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
	try {
	    jsonArray = new JSONArray(data);
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
	} catch (JSONException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return post_tagList;
    }
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
    	String validData = validateData(data);
    	return getRawData(validData);
    }
}