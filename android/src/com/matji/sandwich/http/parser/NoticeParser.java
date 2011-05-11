package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Notice;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class NoticeParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> noticeList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
	try {
	    jsonArray = new JSONArray(data);
	    try{
		JSONObject element;
		for(int i=0 ; i < jsonArray.length() ; i++){
		    element = jsonArray.getJSONObject(i);
		    Notice notice = new Notice();
		    notice.setId(element.getInt("id"));
		    notice.setStart_date(element.getString("start_date"));
		    notice.setEnd_date(element.getString("end_date"));
		    notice.setSubject(element.getString("subject"));
		    notice.setContent(element.getString("content"));
		    notice.setTarget(element.getString("target"));
		    notice.setSequence(element.getInt("sequence"));
		    noticeList.add(notice);
		}
	    } catch(JSONException e){
		throw new JSONMatjiException();
	    }
	} catch (JSONException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return noticeList;
    }

    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	String validData = validateData(data);
	return getRawData(validData);
    }
}