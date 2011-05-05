package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class CommentParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> commentList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		Comment comment = new Comment();
		comment.setId(element.getInt("id"));
		comment.setPost_id(element.getInt("post_id"));
		comment.setUser_id(element.getInt("user_id"));
		comment.setComment(element.getString("comment"));
		comment.setFrom_where(element.getString("from_where"));
		comment.setSequence(element.getInt("sequece"));
		commentList.add(comment);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return commentList;
    }
}