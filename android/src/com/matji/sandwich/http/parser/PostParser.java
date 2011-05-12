package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class PostParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> postList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
	try {
	    jsonArray = new JSONArray(data);
	    try{
		JSONObject element;
		for(int i=0 ; i < jsonArray.length() ; i++){
		    element = jsonArray.getJSONObject(i);
		    Post post = new Post();
		    post.setId(element.getString("id"));
		    post.setUserId(element.getString("user_id"));
		    post.setStoreId(element.getString("store_id"));
		    post.setActivityId(element.getString("activity_id"));
		    post.setPost(element.getString("post"));
		    post.setImageCount(element.getString("image_count"));
		    post.setLikeCount(element.getString("like_count"));
		    post.setCommentCount(element.getString("comment_count"));
		    post.setTagCount(element.getString("tag_count"));
		    post.setLat(element.getLong("lat"));
		    post.setLng(element.getLong("lng"));
		    post.setFromWhere(element.getString("from_where"));
		    postList.add(post);
		}
	    } catch(JSONException e){
		throw new JSONMatjiException();
	    }
	} catch (JSONException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return postList;
    }

    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	String validData = validateData(data);
	return getRawData(validData);
    }
}