package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MessageParser extends MatjiDataParser{
    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> messageList = new ArrayList<MatjiData>();
    	MatjiJSONArray jsonArray;
		try {
			jsonArray = new MatjiJSONArray(data);
			JSONObject element;
			messageList.clear();
			for(int i=0 ; i < jsonArray.length() ; i++){
				element = jsonArray.getMatjiJSONObject(i);
				Message message = new Message();
				message.setId(element.getString("id"));
				message.setSentUserId(element.getString("sent_user_id"));
				message.setSentUserNick((element.getJSONObject("sent_user")).getString("nick"));
				message.setSentUserid((element.getJSONObject("sent_user")).getString("userid"));
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

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}
}