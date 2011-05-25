package com.matji.sandwich.session;

import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.util.*;

import com.matji.sandwich.*;
import com.matji.sandwich.data.*;
import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.MeHttpRequest;

public class Session implements Requestable {
	private volatile static Session session = null;
	private static final int LOGIN = 0;
	private static final int LOGOUT = 1;
	
	private User currentUser;
	private String token;
	
	private PreferenceProvider prefs;
	private Context context;
	
	
	private Session(){}
	private Session(Context context){
		this.context = context;
		this.prefs = new PreferenceProvider(context);
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
	
	public void login(Activity activity, String userid, String password){
		HttpRequestManager manager = new HttpRequestManager(context, this);
		MeHttpRequest request = new MeHttpRequest(context);
		request.actionAuthorize(userid, password);
		manager.request(activity, request, LOGIN);
	}
	
	public boolean logout(){
		return true;
	}
	
//	public boolean isBookmarked(String object, int id){
//		return true;
//	}
//	
//	public boolean isFollowing(int user_id){
//		return true;
//	}
//	
//	public boolean isFollower(int user_id){
//		return true;
//	}
//	
	public boolean isLogin(){
		return (currentUser == null) ? false : true;	
	}
	
	
	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (tag == LOGIN){
				Me me = (Me)data.get(0);
				token = me.getToken();
				currentUser = me.getUser();
		}else if (tag == LOGOUT){
			
		}
		
	}
	
	public void requestExceptionCallBack(int tag, MatjiException e) {
		if (tag == LOGIN){
				currentUser =  null;
				token = null;
		}else if (tag == LOGOUT){
			
		}
		
	}

	
	public String getToken() {
		return token;
	}
	
	public PreferenceProvider getPreferenceProvider() {
		return prefs;
	}
	
	public User getCurrentUser(){
		return currentUser;
	}
	

}
