package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;
import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class CommentParser extends MatjiDataParser{
	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> commentList = new ArrayList<MatjiData>();
		MatjiJSONArray jsonArray;
		try {
			jsonArray = new MatjiJSONArray(data);
			MatjiJSONObject element;
			for (int i = 0 ; i < jsonArray.length(); i++) {
				element = jsonArray.getMatjiJSONObject(i);
				Comment comment = new Comment();
				comment.setComment(element.getString("comment"));
				comment.setCreatedAt(element.getString("created_at"));
				comment.setSequence(element.getString("sequence"));
				comment.setUpdatedAt(element.getString("updated_at"));
				comment.setPostId(element.getString("post_id"));
				comment.setId(element.getString("id"));
				comment.setUserId(element.getString("user_id"));
//				TODO
//				comment.setUser((new UserParser()).getData(data));
				comment.setFromWhere(element.getString("from_where"));
				commentList.add(comment);
			}
		}
		catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.d("Matji", "CommentParser: Parsing success");
		
		return commentList;
	}

	public ArrayList<MatjiData> parseToMatjiDataList(String data) throws MatjiException {
		// TODO 임시
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