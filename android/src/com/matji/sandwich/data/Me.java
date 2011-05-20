package com.matji.sandwich.data;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Me extends MatjiData{
	private User user;
	private ArrayList<MatjiData> likes;
	private ArrayList<MatjiData> bookmarks;
	private String[] followings;
	private String[] followers;
	private String token;
	
	public Me() {
		
	}
	
	public Me(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Me> CREATOR = new Parcelable.Creator<Me>() {
		public Me createFromParcel(Parcel in) {
			return new Me(in);
		}

		public Me[] newArray(int size) {
			return new Me[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeValue(user);
		dest.writeValue(likes);
		dest.writeValue(bookmarks);
		dest.writeValue(followers);
		dest.writeValue(followings);
	}

	
	@SuppressWarnings("unchecked")
	private void readFromParcel(Parcel in) {
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
		likes = ArrayList.class.cast(in.readValue(ArrayList.class.getClassLoader()));
		bookmarks = ArrayList.class.cast(in.readValue(ArrayList.class.getClassLoader()));
		followers = String[].class.cast(in.readValue(String[].class.getClassLoader()));
		followings = String[].class.cast(in.readValue(String[].class.getClassLoader()));
	}

	
	public void setLikes(ArrayList<MatjiData> likes) {
		this.likes = likes;
	}
	
	
	public ArrayList<MatjiData> getLikes() {
		return likes;
	}
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public User getUser() {
		return user;
	}
	
	
	public void setBookmarks(ArrayList<MatjiData> bookmarks) {
		this.bookmarks = bookmarks;
	}
	
	
	public ArrayList<MatjiData> getBookmarks() {
		return bookmarks;
	}
	
	
	public void setFollowings(String[] followings) {
		this.followings = followings;
	}
	
	
	public String[] getFollowings() {
		return followings;
	}
	
	
	public void setFollowers(String[] followers) {
		this.followers = followers;
	}
	
	
	public String[] getFollowers() {
		return followers;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}
