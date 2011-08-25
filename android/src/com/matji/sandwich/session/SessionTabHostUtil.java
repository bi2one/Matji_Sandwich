package com.matji.sandwich.session;

import android.content.Context;

import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.PreferenceProvider;

public class SessionTabHostUtil {
    private Session session;
    private PreferenceProvider preferenceProvider;
    
    public SessionTabHostUtil(Context context) {
	session = Session.getInstance(context);
	preferenceProvider = session.getPreferenceProvider();
    }

    public void setSubTabIndex(int index) {
	preferenceProvider.setInt(SessionIndex.TAB_SUB_INDEX, index);
    }

    public int getSubTabIndex() {
	return preferenceProvider.getInt(SessionIndex.TAB_SUB_INDEX, -1);
    }

    public void flush() {
	setSubTabIndex(-1);
    }
    
    public User getCurrentUser() {
        return session.getCurrentUser();
    }
    
    public boolean isLogin() {
        return session.isLogin();
    }
}