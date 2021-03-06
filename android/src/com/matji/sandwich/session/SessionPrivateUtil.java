package com.matji.sandwich.session;

import com.matji.sandwich.data.provider.ConcretePreferenceProvider;

public class SessionPrivateUtil {
    public static final boolean BASIC_IS_CHECKED = false;
    public static final boolean BASIC_IS_AGREED = false;
    public static final int BASIC_LAST_READ_NOTICE_ID = 0;
    public static final String BASIC_ID = "";

    private ConcretePreferenceProvider mConcretePrefs;
//    private Session session;

    public SessionPrivateUtil(Session session) {
//        this.session = session;
        mConcretePrefs = session.getConcretePreferenceProvider();
    }

    public void clear() {
        
    }
    
    public void notificationClear() {
        setNewAlarmCount(0);
        setNewNoticeCount(0);
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
    
    public void setIsCheckedPopupNotShown(String tag, boolean isChecked) {
        mConcretePrefs.setBoolean(tag, isChecked);
    }
    
    public boolean isCheckedPopupNotShown(String tag) {
        return mConcretePrefs.getBoolean(tag, BASIC_IS_CHECKED);
    }

	public boolean getUserAgree() {
		return mConcretePrefs.getBoolean(SessionIndex.PRIVATE_IS_AGREED_USING_LOCATION_INFO, BASIC_IS_AGREED);
	}
	
	public void setUserAgree(boolean isAgreed) {
		mConcretePrefs.setBoolean(SessionIndex.PRIVATE_IS_AGREED_USING_LOCATION_INFO, isAgreed);
	}
    
}