package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
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
		
		/* Set User External Account */
		UserExternalAccountParser ueaParser = new UserExternalAccountParser();
		user.setExternalAccount(ueaParser.getMatjiData(getObject(object, "external_account")));
		
		/* Set User Mileage */
		UserMileageParser umParser = new UserMileageParser();
		user.setMileage(umParser.getMatjiData(getObject(object, "mileage")));
		
		/* Set Attach Files */
		AttachFileParser afParser = new AttachFileParser();
		ArrayList<MatjiData> dataList = afParser.getMatjiDataList(getArray(object, "attach_files"));
		ArrayList<AttachFile> attach_files = new ArrayList<AttachFile>(); 
		if (dataList != null) {
			for (MatjiData data : dataList)
				attach_files.add((AttachFile) data);
		}
		user.setAttchFiles(attach_files);

		return user;
	}
}