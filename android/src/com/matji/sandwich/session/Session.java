package com.matji.sandwich.session;

import java.io.*;
import java.util.*;

import android.app.Activity;
import android.content.Context;


import com.matji.sandwich.*;
import com.matji.sandwich.data.*;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.MeHttpRequest;

public class Session implements Requestable {
        public static final String STORE_SLIDER_INDEX = "StoreSliderActivity.index";
        public static final String POST_SLIDER_INDEX = "PostSliderActivity.index";
	    
    
	private volatile static Session session = null;
	private static final int AUTHORIZE = 0;
	
	private static final String keyForCurrentUser = "CurrentUser";
	private static final String keyForAccessToken = "AccessToken";
	
	private PreferenceProvider mPrefs;
	private Context mContext;
	private HttpRequestManager mManager;
	private Loginable mLoginable;
	
	private Session(){}
	
	private Session(Context context){
		this.mContext = context;
		this.mPrefs = new PreferenceProvider(context);
	}
	
	
	public static Session getInstance(Context context){
		if(session == null) {
			synchronized(Session.class) {
				if(session == null) {
					session = new Session(context);
				}
			}
		}

	    return session;
	}
	
	
	public void sessionValidate(Loginable loginable, Activity activity){
		this.mLoginable = loginable;
		mManager = new HttpRequestManager(mContext, this);
		MeHttpRequest request = new MeHttpRequest(mContext);
		request.actionMe();
		mManager.request(activity, request, AUTHORIZE);
	}
	
	
	public void login(Loginable loginable, Activity activity, String userid, String password){
		this.mLoginable = loginable;
		mManager = new HttpRequestManager(mContext, this);
		MeHttpRequest request = new MeHttpRequest(mContext);
		request.actionAuthorize(userid, password);
		mManager.request(activity, request, AUTHORIZE);
	}
	
	
	public boolean logout(){
		mPrefs.clear();
		removePrivateDataFromDatabase();
		return mPrefs.commit();
	}
	
	
	private void removePrivateDataFromDatabase(){
		DBProvider dbProvider = DBProvider.getInstance(mContext);
		
		dbProvider.deleteBookmarks();
		dbProvider.deleteLikes();
		dbProvider.deleteFollowers();
		dbProvider.deleteFollowings();
		
	}

	public boolean isLogin(){
		return (mPrefs.getObject(keyForCurrentUser) == null) ? false : true;	
	}
	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (tag == AUTHORIZE){
				Me me = (Me)data.get(0);
				
				try {
					mPrefs.setObject(keyForCurrentUser, me.getUser());
				} catch (NotSerializableException e) {
					e.printStackTrace();
				}
				
				mPrefs.setString(keyForAccessToken, me.getToken());
				mPrefs.commit();
				
				removePrivateDataFromDatabase();
				
				DBProvider dbProvider = DBProvider.getInstance(mContext);
				// TODO 느려지는 부분 ㅜㅜ 
				dbProvider.insertBookmarks(me.getBookmarks());
				dbProvider.insertLikes(me.getLikes());
				dbProvider.insertFollowers(me.getFollowers());
				dbProvider.insertFollowings(me.getFollowings());

				if (mLoginable != null)
					mLoginable.loginCompleted();
		}
		
	}
	
	public void requestExceptionCallBack(int tag, MatjiException e) {
		if (tag == AUTHORIZE){
			if (mLoginable != null)
				mLoginable.loginFailed();
		}
	}

	
	public String getToken() {
		return mPrefs.getString(keyForAccessToken, null);
	}
	
	public PreferenceProvider getPreferenceProvider() {
		return mPrefs;
	}
	
	public User getCurrentUser(){
		Object obj = mPrefs.getObject(keyForCurrentUser);
		if (obj == null)
			return null;
		
		return (User)obj;
	}
}
