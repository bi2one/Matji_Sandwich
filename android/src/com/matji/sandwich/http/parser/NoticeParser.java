package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Notice;
import com.matji.sandwich.exception.MatjiException;

public class NoticeParser extends MatjiDataParser<Notice> {
	protected Notice getMatjiData(JsonObject object) throws MatjiException {
		Notice notice = new Notice();
		notice.setStartDate(getString(object, "start_date"));
		notice.setCreatedAt(getString(object, "created_at"));
		notice.setUpdatedAt(getString(object, "updated_at"));
		notice.setSubject(getString(object, "subject"));
		notice.setId(getInt(object, "id"));
		notice.setContent(getString(object, "content"));
		notice.setTarget(getString(object, "target"));
		notice.setEndDate(getString(object, "end_date"));
		
		return notice;
	}
}