package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class UserParser extends MatjiDataParser<User> {
	protected User getMatjiData(JsonObject object) throws MatjiException {
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
		user.setExternalAccount(new UserExternalAccountParser().getRawObject(getObject(object, "external_account") + ""));
		user.setMileage(new UserMileageParser().getRawObject(getObject(object, "mileage") + ""));

		return user;
	}
}