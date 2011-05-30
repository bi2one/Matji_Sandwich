package com.matji.sandwich.session;

import java.io.*;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.util.*;

import com.matji.sandwich.*;
import com.matji.sandwich.data.*;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.MeHttpRequest;

public class Session implements Requestable {
	private volatile static Session session = null;
	private static final int LOGIN = 0;
	private static final String keyForCurrentUser = "CurrentUser";
	private static final String keyForAccessToken = "AccessToken";
	
	private PreferenceProvider mPrefs;
	private Context mContext;
	private HttpRequestManager mManager;
	private Loginable mLoginableActivity;
	
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
	
	public boolean sessionValidate(){
		return true;
	}
	
	public void login(Loginable loginableActivity, String userid, String password){
		this.mLoginableActivity = loginableActivity;
		mManager = new HttpRequestManager(mContext, this);
		MeHttpRequest request = new MeHttpRequest(mContext);
		request.actionAuthorize(userid, password);
		mManager.request((Activity)loginableActivity, request, LOGIN);
	}
	
	public boolean logout(){
		mPrefs.clear();
		return mPrefs.commit();
	}
	
	
	public boolean isLogin(){
		return (mPrefs.getObject(keyForCurrentUser) == null) ? false : true;	
	}
	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (tag == LOGIN){
				Me me = (Me)data.get(0);
				
				try {
					mPrefs.setObject(keyForCurrentUser, me.getUser());
				} catch (NotSerializableException e) {
					e.printStackTrace();
				}
				
				mPrefs.setString(keyForAccessToken, me.getToken());
				boolean success = mPrefs.commit();
				Log.d("Login", (success == true) ? "success to login" : "failed to login");
				
				DBProvider dbProvider = DBProvider.getInstance(mContext);
				
				dbProvider.deleteBookmarks();
				dbProvider.deleteLikes();
				dbProvider.deleteFollowers();
				dbProvider.deleteFollowings();
				
				dbProvider.insertBookmarks(me.getBookmarks());
				dbProvider.insertLikes(me.getLikes());
				dbProvider.insertFollowers(me.getFollowers());
				dbProvider.insertFollowings(me.getFollowings());
				
				mLoginableActivity.loginCompleted();
		}
		
	}
	
	public void requestExceptionCallBack(int tag, MatjiException e) {
		if (tag == LOGIN){
				mLoginableActivity.loginFailed();
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
