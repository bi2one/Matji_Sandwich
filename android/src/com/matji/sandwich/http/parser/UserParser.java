package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.matji.sandwich.R;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.util.MatjiConstants;

public class UserParser extends MatjiDataParser {
    public User getMatjiData(JsonObject object) throws MatjiException {
        if (object == null) return null;

        User user = new User();
        user.setId(getInt(object, "id"));
        user.setUserid(getString(object, "userid"));
        user.setNick(getString(object, "nick"));
        user.setEmail(getString(object, "email"));

        /* Set Title */
        String tmp = getString(object, "title");
        if (tmp == null) {
            user.setTitle(
			  String.format(MatjiConstants.string(R.string.default_string_title), user.getNick()));
        } else {
            user.setTitle(tmp);
        }

        /* Set Intro */
        tmp = getString(object, "intro");
        if (tmp == null) {
            user.setIntro("");
        } else {
            user.setIntro(tmp);
        }

        /* Set Intro */
        tmp = getString(object, "website");
        if (tmp == null) {
            user.setWebsite("");
        } else {
            user.setWebsite(tmp);
        }
        user.setPostCount(getInt(object, "post_count"));
        user.setTagCount(getInt(object, "tag_count"));
        user.setLikeStoreCount(getInt(object, "like_store_count"));
        user.setDiscoverStoreCount(getInt(object, "discover_store_count"));
        user.setBookmarkStoreCount(getInt(object, "bookmark_store_count"));
        user.setFollowingCount(getInt(object, "following_count"));
        user.setFollowerCount(getInt(object, "follower_count"));
        user.setReceivedMessageCount(getInt(object, "message_count"));
        user.setImageCount(getInt(object, "image_count"));
        user.setFollowing(getBoolean(object, "following"));
        user.setFollowed(getBoolean(object, "followed"));

        /* Set User External Account */
        UserExternalAccountParser ueaParser = new UserExternalAccountParser();
        user.setExternalAccount(ueaParser.getMatjiData(getObject(object, "external_account")));

        /* Set User Mileage */
        UserMileageParser umParser = new UserMileageParser();
        user.setMileage(umParser.getMatjiData(getObject(object, "user_mileage")));

        /* Set Post */
        PostParser postParser = new PostParser();
        user.setPost(postParser.getMatjiData(getObject(object, "post")));

        /* Set Attach Files */
        AttachFileParser afParser = new AttachFileParser();
        ArrayList<MatjiData> dataList = afParser.getMatjiDataList(getArray(object, "attach_files"));
        ArrayList<AttachFile> attach_files = new ArrayList<AttachFile>(); 
        if (dataList != null) {
            for (MatjiData data : dataList)
                attach_files.add((AttachFile) data);
        }
        user.setAttchFiles(attach_files);
        user.setCountryCode(getString(object, "country_code"));

        return user;
    }
}