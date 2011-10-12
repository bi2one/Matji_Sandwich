package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.UserMileage;
import com.matji.sandwich.exception.MatjiException;

public class UserMileageParser extends MatjiDataParser {
	public UserMileage getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		UserMileage mileage = new UserMileage();
		mileage.setId(getInt(object, "id"));
		mileage.setUserId(getInt(object, "user_id"));
		mileage.setTotalPoint(getInt(object, "total_point"));
		mileage.setGrade(getString(object, "grade"));
		
		/* Set User */
		UserParser userPaser = new UserParser();
		mileage.setUser(userPaser.getMatjiData(getObject(object, "user")));

		return mileage;
	}
}