package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.UserMileage;
import com.matji.sandwich.exception.MatjiException;

public class UserMileageParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		UserMileage mileage = new UserMileage();
		mileage.setId(getInt(object, "id"));
		mileage.setUserId(getInt(object, "user_id"));
		mileage.setTotalPoint(getInt(object, "total_point"));
		mileage.setGrade(getString(object, "grade"));
		mileage.setUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));

		return mileage;
	}
}