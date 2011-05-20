package com.matji.sandwich.session;

import android.content.Context;
import com.matji.sandwich.data.CurrentUser;
import com.matji.sandwich.data.provider.PreferenceProvider;

public class Session {
	private volatile static Session session = null;
	private static CurrentUser currentUser;
	@SuppressWarnings("unused")
	private static PreferenceProvider prefs;
	@SuppressWarnings("unused")
	private Context context;
	
	
	private Session(){}
	private Session(Context context){
		this.context = context;
	}
	

	public static Session getInstance(Context context){
		if(session == null) {
			synchronized(Session.class) {
				if(session == null) {
					session = new Session(context);
					prefs = new PreferenceProvider(context);

					// For test
					currentUser = new CurrentUser();
				}
			}
		}

	    return session;
	}
	
	public boolean sessionValidate(){
		return true;
	}
	
	public boolean login(){
		return true;
	}
	
	public boolean logout(){
		return true;
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
