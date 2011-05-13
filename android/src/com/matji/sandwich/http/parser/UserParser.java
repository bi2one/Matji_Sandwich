package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.UserExternalAccount;
import com.matji.sandwich.data.UserMileage;
import com.matji.sandwich.exception.MatjiException;

public class UserParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
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
		user.setExternalAccount((UserExternalAccount) new UserExternalaccountParser().getRawObject(getString(object, "external_account")));
		user.setMileage((UserMileage) new UserMileageParser().getRawObject(getString(object, "mileage")));

		return user;
	}
}