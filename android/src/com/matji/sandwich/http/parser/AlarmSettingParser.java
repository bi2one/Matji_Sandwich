package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

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

		Log.d("Parser", "AlarmPermitParser:: called getMatjiData");
		
		return alarmSetting;
	}
}