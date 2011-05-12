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
	private String action;

	public CommentParser(String action) {
		this.action = action;
	}

	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> commentList = new ArrayList<MatjiData>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
			try{
				JSONObject element;
				if (action.equals("new") & action.equals("list")) {
					for(int i=0 ; i < jsonArray.length() ; i++){
						element = jsonArray.getJSONObject(i);
						Comment comment = new Comment();
						comment.setId(element.getInt("id"));
						comment.setPost_id(element.getInt("post_id"));
						comment.setUser_id(element.getInt("user_id"));
						comment.setComment(element.getString("comment"));
						comment.setFrom_where(element.getString("from_where"));
						comment.setSequence(element.getInt("sequence"));
						commentList.add(comment);
					}
				}
				else if (action.equals("delete"));
			} catch(JSONException e){
				throw new JSONMatjiException();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return commentList;
	}

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}
}