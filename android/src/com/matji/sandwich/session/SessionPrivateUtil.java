package com.matji.sandwich.session;

import android.content.Context;

import com.matji.sandwich.data.provider.ConcretePreferenceProvider;

public class SessionPrivateUtil {

    public static final String BASIC_ID = "";
    public static final int BASIC_LAST_READ_NOTICE_ID = 0;
    public static final boolean BASIC_IS_CHECKED = false;

    private ConcretePreferenceProvider mConcretePrefs;
//    private Session session;

    public SessionPrivateUtil(Context context, Session session) {
//        this.session = session;
        mConcretePrefs = session.getConcretePreferenceProvider();
    }

    public void clear() {
        
    }

    public void setLastReadNoticeId(int lastReadNoticeId) {
        mConcretePrefs.setInt(SessionIndex.PRIVATE_LAST_READ_NOTICE_ID, lastReadNoticeId);
    }

    public int getLastReadNoticeId() {
        return mConcretePrefs.getInt(SessionIndex.PRIVATE_LAST_READ_NOTICE_ID, BASIC_LAST_READ_NOTICE_ID);
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
    
    public void setSavedUserId(String user_id) {
        mConcretePrefs.setString(SessionIndex.PRIVATE_SAVED_USER_ID, user_id);
    }
    
    public String getSavedUserId() {
        return mConcretePrefs.getString(SessionIndex.PRIVATE_SAVED_USER_ID, BASIC_ID);
    }
    
    public void setIsCheckedSaveId(boolean isChecked) {
        mConcretePrefs.setBoolean(SessionIndex.PRIVATE_IS_CHECKED_SAVE_USER_ID, isChecked);
    }
    
    public boolean isCheckedSaveId() {
        return mConcretePrefs.getBoolean(SessionIndex.PRIVATE_IS_CHECKED_SAVE_USER_ID, BASIC_IS_CHECKED);
    }
}