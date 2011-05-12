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
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> messageList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
			try{
			    JSONObject element;
			    for(int i=0 ; i < jsonArray.length() ; i++){
				element = jsonArray.getJSONObject(i);
				Message message = new Message();
				message.setId(element.getString("id"));
				message.setSentUserId((element.getJSONObject("sent_user")).getString("sent_user_id"));
				//message.setReceived_user_id(element.getInt("received_user_id"));
				//message.setMessage(element.getString("message"));
				//message.setSequence(element.getInt("sequence"));
				messageList.add(message);
			    }
			} catch(JSONException e){
			    throw new JSONMatjiException();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	return messageList;
    }

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}
}