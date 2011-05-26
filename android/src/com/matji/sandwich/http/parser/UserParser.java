package com.matji.sandwich.http.parser;

import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.UserExternalAccount;
import com.matji.sandwich.data.UserMileage;
import com.matji.sandwich.exception.MatjiException;

public class UserParser extends MatjiDataParser {
	protected User getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		User user = new User();
		user.setId(getInt(object, "id"));
		user.setUserid(getString(object, "userid"));
		user.setNick(getString(object, "nick"));
		user.setEmail(getString(object, "email"));
		user.setTitle(getString(object, "title"));
		user.setIntro(getString(object, "intro"));
		user.setPostCount(getInt(object, "post_count"));
		user.setTagCount(getInt(object, "tag_count"));
		user.setStoreCount(getInt(object, "store_count"));
		user.setFollowingCount(getInt(object, "following_count"));
		user.setFollowerCount(getInt(object, "follower_count"));
		user.setFollowing(getBoolean(object, "following"));
		user.setFollowed(getBoolean(object, "followed"));
		user.setExternalAccount((UserExternalAccount) new UserExternalAccountParser().getRawObject(getObject(object, "external_account") + ""));
		user.setMileage((UserMileage) new UserMileageParser().getRawObject(getObject(object, "mileage") + ""));

		Log.d("Matji", "UserParser END");
		return user;
	}
}