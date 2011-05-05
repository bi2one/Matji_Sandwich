package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class AttachFileParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> attach_fileList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		AttachFile attach_file = new AttachFile();
		attach_file.setId(element.getInt("id"));
		attach_file.setUser_id(element.getInt("user_id"));
		attach_file.setStore_id(element.getInt("store_id"));
		attach_file.setPost_id(element.getInt("post_id"));
		attach_file.setFilename(element.getString("filename"));
		attach_file.setFullpath(element.getString("fullpath"));
		attach_file.setSequence(element.getInt("sequence"));
		attach_fileList.add(attach_file);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return attach_fileList;
    }
}