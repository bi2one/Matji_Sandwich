package com.matji.sandwich.http.parser;

import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Me;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class MeParser extends MatjiDataParser{

	@Override
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		Me me = new Me();
		
		me.setUser((User) new UserParser().getMatjiData(getObject(object, "user")));
		me.setBookmarks((ArrayList<MatjiData>) new BookmarkParser().getMatjiDataList(getObject(object, "bookmarks")));
		me.setLikes((ArrayList<MatjiData>) new LikeParser().getMatjiDataList(getObject(object, "likes")));
		
		String followersIds = getString(object, "followers");
		String followingsIds = getString(object, "followings");		
		
		me.setFollowers(followersIds.split(","));
		me.setFollowings(followingsIds.split(","));
		
		return me;
	}

}
