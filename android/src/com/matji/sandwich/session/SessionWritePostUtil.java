package com.matji.sandwich.session;

import java.io.File;
import java.io.NotSerializableException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.data.provider.PreferenceProvider;

public class SessionWritePostUtil {
    public static final int BASIC_STORE_ID = -1;
    public static final String BASIC_POST = "";
    public static final String BASIC_TAGS = "";
    private Session session;
    private PreferenceProvider preferenceProvider;
    
    public SessionWritePostUtil(Context context) {
	session = Session.getInstance(context);
	preferenceProvider = session.getPreferenceProvider();
    }

    public void clear() {
	setPost(BASIC_POST);
	setStoreId(BASIC_STORE_ID);
	setTags(BASIC_TAGS);
	setPictureFiles(null);
    }

    public void setTags(String tags) {
	preferenceProvider.setString(SessionIndex.WRITE_POST_TAGS, tags.trim());
    }

    public String getTags() {
	return preferenceProvider.getString(SessionIndex.WRITE_POST_TAGS, BASIC_TAGS);
    }

    public void setPost(String post) {
	preferenceProvider.setString(SessionIndex.WRITE_POST_POST, post.trim());
    }

    public String getPost() {
	return preferenceProvider.getString(SessionIndex.WRITE_POST_POST, BASIC_POST);
    }

    public void setStoreId(int storeId) {
	preferenceProvider.setInt(SessionIndex.WRITE_POST_STORE_ID, storeId);
    }

    public int getStoreId() {
	return preferenceProvider.getInt(SessionIndex.WRITE_POST_STORE_ID, BASIC_STORE_ID);
    }

    public void setPictureFiles(ArrayList<File> files) {
	try {
	    preferenceProvider.setObject(SessionIndex.WRITE_POST_IMAGES, files);
	} catch(NotSerializableException e) {
	    Log.e("Matji", e.toString());
	}
    }

    @SuppressWarnings("unchecked")
    public ArrayList<File> getPictureFiles() {
	return (ArrayList<File>)preferenceProvider.getObject(SessionIndex.WRITE_POST_IMAGES);
    }
}