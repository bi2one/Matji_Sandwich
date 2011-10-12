package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AlarmSetting;
import com.matji.sandwich.exception.MatjiException;

public class AlarmSettingParser extends MatjiDataParser {
	public AlarmSetting getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		AlarmSetting alarmSetting = new AlarmSetting();
		alarmSetting.setId(getInt(object, "id"));
		alarmSetting.setUserId(getInt(object, "user_id"));
		alarmSetting.setComment(getBoolean(object, "comment"));
		alarmSetting.setFollowing(getBoolean(object, "following"));
		alarmSetting.setLikepost(getBoolean(object, "likepost"));
		alarmSetting.setMessage(getBoolean(object, "message"));
		
		return alarmSetting;
	}
}