package com.matji.sandwich.session;

import android.content.Context;

import com.matji.sandwich.data.provider.ConcretePreferenceProvider;

public class SessionPrivateUtil {

    public static final String BASIC_ID = "";
    public static final int BASIC_LAST_READ_ID = 0;
    public static final int BASIC_LAST_READ_ALARM_ID = 0;
    public static final int BASIC_LAST_READ_NOTICE_ID = 0;
    public static final boolean BASIC_IS_LOGIN = false;

    private ConcretePreferenceProvider mConcretePrefs;
    private Session session;

    public SessionPrivateUtil(Context context, Session session) {
        this.session = session;
        mConcretePrefs = session.getConcretePreferenceProvider();
    }

    public void clear() {
        setLastReadMessageId(BASIC_LAST_READ_ID);
    }

    public void setLastReadMessageId(int lastReadMessageId) {
        mConcretePrefs.setInt(session.getCurrentUser().getId()+"_"+SessionIndex.PRIVATE_LAST_READ_MESSAGE_ID, lastReadMessageId);
    }

    public int getLastReadMessageId() {
        return mConcretePrefs.getInt(session.getCurrentUser().getId()+"_"+SessionIndex.PRIVATE_LAST_READ_MESSAGE_ID, BASIC_LAST_READ_ID);
    }

    public void setLastReadAlarmId(int lastReadAlarmId) {
        mConcretePrefs.setInt(session.getCurrentUser().getId()+"_"+SessionIndex.PRIVATE_LAST_READ_ALARM_ID, lastReadAlarmId);
    }

    public int getLastReadAlarmId() {
        return mConcretePrefs.getInt(session.getCurrentUser().getId()+"_"+SessionIndex.PRIVATE_LAST_READ_ALARM_ID, BASIC_LAST_READ_ALARM_ID);
    }    
    public void setLastReadNoticeId(int lastReadNoticeId) {
        mConcretePrefs.setInt(SessionIndex.PRIVATE_LAST_READ_NOTICE_ID, lastReadNoticeId);
    }

    public int getLastReadNoticeId() {
        return mConcretePrefs.getInt(SessionIndex.PRIVATE_LAST_READ_NOTICE_ID, BASIC_LAST_READ_NOTICE_ID);
    }

    public void setLastLoginUserNick(String id) {
        mConcretePrefs.setString(SessionIndex.PRIVATE_LAST_LOGIN_USER_ID, id);
    }

    public String getLastLoginUserNick() {
        return mConcretePrefs.getString(SessionIndex.PRIVATE_LAST_LOGIN_USER_ID, BASIC_ID);
    }

    public void setLastLoginState(boolean isLogin) {
        mConcretePrefs.setBoolean(SessionIndex.PRIVATE_IS_LOGIN, isLogin);
    }
    
    public boolean getLastLoginState() {
        return mConcretePrefs.getBoolean(SessionIndex.PRIVATE_IS_LOGIN, false);
    }

    public void setNewAlarmCount(int new_alarm_count) {
        mConcretePrefs.setInt(SessionIndex.PRIVATE_NEW_ALARM_COUNT, new_alarm_count);
    }
 
    public int getNewAlarmCount() {
        return mConcretePrefs.getInt(SessionIndex.PRIVATE_NEW_ALARM_COUNT, 0);
    }

    public void setNewNoticeCount(int new_notice_count) {
        mConcretePrefs.setInt(SessionIndex.PRIVATE_NEW_NOTICE_COUNT, new_notice_count);
    }
    
    public int getNewNoticeCount() {
        return mConcretePrefs.getInt(SessionIndex.PRIVATE_NEW_NOTICE_COUNT, 0);
    }
}