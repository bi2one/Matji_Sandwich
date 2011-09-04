package com.matji.sandwich.session;

import android.content.Context;

import com.matji.sandwich.data.provider.ConcretePreferenceProvider;

public class SessionPrivateUtil {

    public static final String BASIC_ID = "";
    public static final int BASIC_LAST_READ_ID = 0;
    private ConcretePreferenceProvider mConcretePrefs;

    public SessionPrivateUtil(Context context, Session session) {
        mConcretePrefs = session.getConcretePreferenceProvider();
    }

    public void clear() {
        setLastReadMessageId(BASIC_LAST_READ_ID);
    }
    
    public void setLastReadMessageId(int lastReadMessageId) {
        mConcretePrefs.setInt(SessionIndex.PRIVATE_LAST_READ_MESSAGE_ID, lastReadMessageId);
    }
    
    public int getLastReadMessageId() {
        return mConcretePrefs.getInt(SessionIndex.PRIVATE_LAST_READ_MESSAGE_ID, BASIC_LAST_READ_ID);
    }
    
    public void setLastLoginUserid(String id) {
        mConcretePrefs.setString(SessionIndex.PRIVATE_LAST_LOGIN_USER_ID, id);
    }
    
    public String getLastLoginUserid() {
        return mConcretePrefs.getString(SessionIndex.PRIVATE_LAST_LOGIN_USER_ID, BASIC_ID);
    }
}