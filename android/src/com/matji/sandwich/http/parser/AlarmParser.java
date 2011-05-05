package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class AlarmParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> alarmList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		Alarm alarm = new Alarm();
		alarm.setId(element.getInt("id"));
		alarm.setReceived_user_id(element.getInt("received_user_id"));
		alarm.setSent_user_id(element.getInt("sent_user_id"));
		alarm.setAlarm_type(element.getString("alarm_type"));
		alarm.setSequence(element.getInt("sequence"));
		alarmList.add(alarm);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return alarmList;
    }
}