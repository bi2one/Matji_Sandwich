package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;
import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class PostParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> postList = new ArrayList<MatjiData>();
    	MatjiJSONArray jsonArray;
	try {
	    jsonArray = new MatjiJSONArray(data);
	    MatjiJSONObject element;
	    postList.clear();
		for(int i=0 ; i < jsonArray.length() ; i++){
		    element = jsonArray.getMatjiJSONObject(i);
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
		    post.setCreatedAt(element.getString("created_at"));
		    post.setUpdatedAt(element.getString("updated_at"));
		    postList.add(post);
		}
	} catch (JSONException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return postList;
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