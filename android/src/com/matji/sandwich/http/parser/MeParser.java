package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.util.Log;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AlarmSetting;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Me;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class MeParser extends MatjiDataParser {
	public Me getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Me me = new Me();
		
		MatjiData matjiData = null;
		ArrayList<MatjiData> matjiDataList = null;
		
		
		/* Set User */
		UserParser userParser = new UserParser();
		matjiData = userParser.getMatjiData(getObject(object, "user"));
		if (matjiData != null)
			me.setUser((User)matjiData);
		
		/* Set Bookmarks */
		BookmarkParser bookmarkParser = new BookmarkParser();
		matjiDataList = bookmarkParser.getMatjiDataList(getArray(object, "bookmarks"));
		if (matjiDataList != null){
			ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
			for (MatjiData data : matjiDataList) {
				bookmarks.add((Bookmark) data);
			}
			me.setBookmarks(bookmarks);
		}

		/* Set Likes */
		LikeParser likeParser = new LikeParser();
		matjiDataList = likeParser.getMatjiDataList(getArray(object, "likes"));
		if (matjiDataList != null){
			ArrayList<Like> likes = new ArrayList<Like>();
			for (MatjiData data : matjiDataList) {
				likes.add((Like) data);
			}
			me.setLikes(likes);
		}

		/* Set Follow */
		String followers = getString(object, "followers");
		String followings = getString(object, "followings");
		
		
		if (followers != null)
			me.setFollowers(followers.split(","));
		if (followings != null)
			me.setFollowings(followings.split(","));
		
		/* Set Access token */
		String token = getString(object, "access_token");
		if (token != null)
			me.setToken(token);

		/* Set Alarm Setting */
		AlarmSettingParser alarmSettingParser = new AlarmSettingParser();
		matjiData = alarmSettingParser.getMatjiData(getObject(object, "user_alarm_setting"));
        if (matjiData != null)
            me.getUser().setAlarmSetting((AlarmSetting) matjiData);
		
		Log.d("Parser", "MeParser:: called getMatjiData");
		
		return me;
	}
}