package com.matji.sandwich.data;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Me extends MatjiData{
	private User user;
	private ArrayList<Like> likes;
	private ArrayList<Bookmark> bookmarks;
	private String[] followings;
	private String[] followers;
	private String token;
	private boolean agree_term;
	
	public Me() {}
	
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
    public void readFromParcel(Parcel in) {
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
		likes = ArrayList.class.cast(in.readValue(ArrayList.class.getClassLoader()));
		bookmarks = ArrayList.class.cast(in.readValue(ArrayList.class.getClassLoader()));
		followers = String[].class.cast(in.readValue(String[].class.getClassLoader()));
		followings = String[].class.cast(in.readValue(String[].class.getClassLoader()));
	}

	
	public void setLikes(ArrayList<Like> likes) {
		this.likes = likes;
	}
	
	
	public ArrayList<Like> getLikes() {
		return likes;
	}
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public User getUser() {
		return user;
	}
	
    public void setBookmarks(ArrayList<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}
	
	
	public ArrayList<Bookmark> getBookmarks() {
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

    public void setAgreeTerm(boolean agree_term) {
        this.agree_term = agree_term;
    }

    public boolean isAgreeTerm() {
        return agree_term;
    }	
}
