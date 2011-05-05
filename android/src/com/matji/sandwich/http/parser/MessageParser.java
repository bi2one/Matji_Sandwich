package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MessageParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> messageList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		Message message = new Message();
		message.setId(element.getInt("id"));
		message.setSent_user_id(element.getInt("sent_user_id"));
		message.setReceived_user_id(element.getInt("received_user_id"));
		message.setMessage(element.getString("message"));
		message.setSequence(element.getInt("sequence"));
		messageList.add(message);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return messageList;
    }
}