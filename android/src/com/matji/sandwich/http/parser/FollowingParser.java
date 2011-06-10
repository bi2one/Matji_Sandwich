package com.matji.sandwich.http.parser;

import android.content.Context;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Following;
import com.matji.sandwich.exception.MatjiException;

public class FollowingParser extends MatjiDataParser {
	public FollowingParser(Context context) {
		super(context);
	}

	protected Following getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Following following = new Following();
		following.setCreatedAt(getString(object, "created_at"));
		following.setFollowingUserId(getInt(object, "following_user_id"));
		following.setUpdatedAt(getString(object, "updated_at"));
		following.setFollowedUserId(getInt(object, "followed_user_id"));
		following.setId(getInt(object, "id"));
		following.setIntro(getString(object, "intro"));
		following.setTitle(getString(object, "title"));
		following.setNick(getString(object, "nick"));
		following.setFollowingCount(getInt(object, "following_count"));
		following.setFollowerCount(getInt(object, "follower_count"));
		following.setTagCount(getInt(object, "tag_count"));
		following.setUserid(getString(object, "userid"));
		following.setPostCount(getInt(object, "post_count"));
		following.setStoreCount(getInt(object, "store_count"));
		
		/* Set Follow User */
		UserParser userParser = new UserParser(context);
		following.setFollowingUser(userParser.getMatjiData(getObject(object, "following_user")));
		following.setFollowedUser(userParser.getMatjiData(getObject(object, "followed_user")));

		return following;
	}
}