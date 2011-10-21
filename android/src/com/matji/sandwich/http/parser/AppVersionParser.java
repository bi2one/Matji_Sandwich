package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AppVersion;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

public class AppVersionParser extends MatjiDataParser {

	@Override
	public AppVersion getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		AppVersion app_version = new AppVersion();
		app_version.setId(getInt(object, "id"));
		app_version.setTarget(getString(object, "target"));
		app_version.setUrgent(getBoolean(object, "urgent"));
		app_version.setVersion(getString(object, "version"));
		return app_version;
	}

}
