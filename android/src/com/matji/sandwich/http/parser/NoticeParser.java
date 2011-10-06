package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Notice;
import com.matji.sandwich.exception.MatjiException;

public class NoticeParser extends MatjiDataParser {
	public NoticeParser(Context context) {
		super(context);
		this.context = context;
	}

	public Notice getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;

		Notice notice = new Notice();
		
		notice.setStartDate(getString(object, "start_date"));
		notice.setCreatedAt(getString(object, "created_at"));
		notice.setUpdatedAt(getString(object, "updated_at"));
		notice.setSubject(getString(object, "subject"));
		notice.setId(getInt(object, "id"));
		notice.setContent(getString(object, "content"));
		notice.setTarget(getString(object, "target"));
		notice.setEndDate(getString(object, "end_date"));
		notice.setAgo(getLong(object, "ago"));

		Log.d("Parser", "NoticeParser:: called getMatjiData");

		return notice;
	}
}