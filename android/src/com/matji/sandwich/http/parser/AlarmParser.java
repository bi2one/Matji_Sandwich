package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.exception.MatjiException;

public class AlarmParser extends MatjiDataParser {
	protected Alarm getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Alarm alarm = new Alarm();
		alarm.setId(getInt(object, "id"));
		alarm.setReceivedUserId(getInt(object, "received_user_id"));
		alarm.setSentUserId(getInt(object, "sent_user_id"));
		alarm.setAlarmType(getString(object, "alarm_type"));
		
		/* Set Sent User*/
		UserParser userParser = new UserParser();
		alarm.setSentUser(userParser.getMatjiData(getObject(object, "sent_user")));
		
		/* Set Received User*/
		alarm.setReceivedUser(userParser.getMatjiData(getObject(object, "received_user")));
		
		alarm.setCreatedAt(getString(object, "created_at"));
		alarm.setUpdatedAt(getString(object, "updated_at"));
		
		return alarm;
	}
}