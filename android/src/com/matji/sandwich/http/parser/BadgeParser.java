package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Badge;
import com.matji.sandwich.exception.MatjiException;

public class BadgeParser extends MatjiDataParser {
	public BadgeParser(Context context) {
		super(context);
		this.context = context;
	}

	protected Badge getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;

		Badge badge = new Badge();
		
		badge.setNewNoticeCount(getInt(object, "new_notice_count"));
		badge.setNewAlarmCount(getInt(object, "new_alarm_count"));

		Log.d("Parser", "BadgeParser:: called getMatjiData");

		return badge;
	}
}