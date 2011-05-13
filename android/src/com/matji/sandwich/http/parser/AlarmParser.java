package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class AlarmParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		Alarm alarm = new Alarm();
		alarm.setId(getInt(object, "id"));
		alarm.setReceivedUserId(getInt(object, "received_user_id"));
		alarm.setSentUserId(getInt(object, "sent_user_id"));
		alarm.setAlarmType(getString(object, "alarm_type"));
		alarm.setSentUser((User) new UserParser().getRawObject(getString(object, "sent_user")));
		alarm.setReceivedUser((User) new UserParser().getRawObject(getString(object, "received_user")));
		alarm.setCreatedAt(getString(object, "created_at"));
		alarm.setUpdatedAt(getString(object, "updated_at"));
		
		return alarm;
	}
}