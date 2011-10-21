package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Badge;
import com.matji.sandwich.exception.MatjiException;

public class BadgeParser extends MatjiDataParser {
	public Badge getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;

		Badge badge = new Badge();
		
		badge.setNewNoticeCount(getInt(object, "new_notice_count"));
		badge.setNewAlarmCount(getInt(object, "new_alarm_count"));

		AppVersionParser avp = new AppVersionParser();
		
		badge.setNewVersion(avp.getMatjiData(getObject(object, "version")));
		return badge;
	}
}