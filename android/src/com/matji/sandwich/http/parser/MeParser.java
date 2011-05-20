package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Me;
import com.matji.sandwich.exception.MatjiException;

public class MeParser extends MatjiDataParser<Me> {
	protected Me getMatjiData(JsonObject object) throws MatjiException {
		Me me = new Me();
		
		me.setUser(new UserParser().getMatjiData(getObject(object, "user")));
		me.setBookmarks(new BookmarkParser().getMatjiDataList(getObject(object, "bookmarks")));
		me.setLikes(new LikeParser().getMatjiDataList(getObject(object, "likes")));
		me.setFollowers(getString(object, "followers").split(","));
		me.setFollowings(getString(object, "followings").split(","));
		
		return me;
	}
}
