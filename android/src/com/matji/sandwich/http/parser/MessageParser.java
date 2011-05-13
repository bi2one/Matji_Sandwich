package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;

import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class MessageParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> messageList = new ArrayList<MatjiData>();
    	MatjiJSONArray jsonArray;
	try {
	    jsonArray = new MatjiJSONArray(data);
	    MatjiJSONObject element;
	    messageList.clear();
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getMatjiJSONObject(i);
		Message message = new Message();
		message.setId(element.getString("id"));
		message.setSentUserId(element.getString("sent_user_id"));
		message.setSentUserNick((element.getMatjiJSONObject("sent_user")).getString("nick"));
		message.setSentUserid((element.getMatjiJSONObject("sent_user")).getString("userid"));
		message.setReceivedUserId(element.getString("received_user_id"));
		message.setMessage(element.getString("message"));
		messageList.add(message);
	    }
	} catch (JSONException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return messageList;
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