package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Me;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class MeParser extends MatjiDataParser {
	protected Me getMatjiData(JsonObject object) throws MatjiException {
		Me me = new Me();
		
		me.setUser((User) new UserParser().getMatjiData(getObject(object, "user")));
		
		ArrayList<MatjiData> dataList = new BookmarkParser().getMatjiDataList(getObject(object, "bookmarks"));
		ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
		for (MatjiData data : dataList) {
			bookmarks.add((Bookmark) data);
		}
		me.setBookmarks(bookmarks);

		dataList = new LikeParser().getMatjiDataList(getObject(object, "likes"));
		ArrayList<Like> likes = new ArrayList<Like>();
		for (MatjiData data : dataList) {
			likes.add((Like) data);
		}
		me.setLikes(likes);

		me.setFollowers(getString(object, "followers").split(","));
		me.setFollowings(getString(object, "followings").split(","));
		
		return me;
	}
}
