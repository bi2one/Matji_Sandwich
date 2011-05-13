package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;
import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class TagParser extends MatjiDataParser{
	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> tagList = new ArrayList<MatjiData>();
		MatjiJSONArray jsonArray;
		try {
			jsonArray = new MatjiJSONArray(data);
			MatjiJSONObject element;
			for (int i = 0 ; i < jsonArray.length(); i++) {
				element = jsonArray.getMatjiJSONObject(i);
				Tag tag = new Tag();
				tag.setTag(element.getString("tag"));
				tag.setCreated_at(element.getString("created_at"));
				tag.setSequence(element.getString("sequence"));
				tag.setUpdatedAt(element.getString("updated_at"));
				tag.setId(element.getString("id"));
				tagList.add(tag);
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		Log.d("Matji", "TagParser: Parsing success");
		
		return tagList;
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