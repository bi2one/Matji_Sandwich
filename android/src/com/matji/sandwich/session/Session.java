package com.matji.sandwich.session;

import android.content.Context;

import com.matji.sandwich.data.CurrentUser;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.data.provider.PreferenceProvider;

public class Session {
	private volatile static Session session = null;
	private static CurrentUser currentUser;
	private static PreferenceProvider prefs;
	private Context context;
	
	private Session(Context context){
		this.context = context;
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
	
	public void login(){
	
	}
	
	public void logout(){
		
	}
	
	public boolean isBookmarked(String object, int id){
		return true;
	}
	
	public boolean isFollowing(int user_id){
		return true;
	}
	
	public boolean isFollower(int user_id){
		return true;
	}
	
	public boolean isLogin(){
		return (currentUser == null) ? false : true;	
	}
	
	public CurrentUser getCurrentUser(){
		return currentUser;
	}
	
}
