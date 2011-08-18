package com.matji.sandwich.session;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.data.provider.PreferenceProvider;

public class SessionWritePostUtil {
    public static final int BASIC_STORE_ID = -1;
    public static final String BASIC_POST = "";
    private Session session;
    private PreferenceProvider preferenceProvider;
    
    public SessionWritePostUtil(Context context) {
	session = Session.getInstance(context);
	preferenceProvider = session.getPreferenceProvider();
    }

    public void setPost(String post) {
	if (!post.trim().equals("")) {
	    // Log.d("=====", "save post: " + post);
	    preferenceProvider.setString(SessionIndex.WRITE_POST_POST, post.trim());
	}
    }

    public String getPost() {
	return preferenceProvider.getString(SessionIndex.WRITE_POST_POST, BASIC_POST);
    }

    public void setStoreId(int storeId) {
	// Log.d("=====", "save store id: " + storeId);
	preferenceProvider.setInt(SessionIndex.WRITE_POST_STORE_ID, storeId);
    }

    public int getStoreId() {
	return preferenceProvider.getInt(SessionIndex.WRITE_POST_STORE_ID, BASIC_STORE_ID);
    }
}