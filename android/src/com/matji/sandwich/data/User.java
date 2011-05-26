package com.matji.sandwich.data;

import java.io.*;

import android.os.Parcel;
import android.os.Parcelable;

public class User extends MatjiData implements Serializable{
	private static final long serialVersionUID = 3938226042478924177L;
	private int id;
	private String userid;
	private String nick;
	private String email;
	private String title;
	private String intro;
	private int post_count;
	private int tag_count;
	private int store_count;
	private int following_count;
	private int follower_count;
	private boolean following;
	private boolean followed;
	private UserExternalAccount external_account;
	private UserMileage mileage;

	public User() {}

	public User(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeString(userid);
		dest.writeString(nick);
		dest.writeString(email);
		dest.writeString(title);
		dest.writeString(intro);
		dest.writeInt(post_count);
		dest.writeInt(tag_count);
		dest.writeInt(store_count);
		dest.writeInt(following_count);
		dest.writeInt(follower_count);
		dest.writeInt(following ? 1 : 0);
		dest.writeInt(followed ? 1 : 0);
		dest.writeValue(external_account);
		dest.writeValue(mileage);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		userid = in.readString();
		nick = in.readString();
		email = in.readString();
		title = in.readString();
		intro = in.readString();
		post_count = in.readInt();
		tag_count = in.readInt();
		store_count = in.readInt();
		following_count = in.readInt();
		follower_count = in.readInt();
		following = in.readInt() != 0;
		followed = in.readInt() != 0;
		external_account = UserExternalAccount.class.cast(in.readValue(UserExternalAccount.class.getClassLoader()));
		mileage = UserMileage.class.cast(in.readValue(UserMileage.class.getClassLoader()));
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIntro() {
		return intro;
	}

	public void setPostCount(int post_count) {
		this.post_count = post_count;
	}

	public int getPostCount() {
		return post_count;
	}

	public void setTagCount(int tag_count) {
		this.tag_count = tag_count;
	}

	public int getTagCount() {
		return tag_count;
	}

	public void setStoreCount(int store_count) {
		this.store_count = store_count;
	}

	public int getStoreCount() {
		return store_count;
	}

	public void setFollowingCount(int following_count) {
		this.following_count = following_count;
	}

	public int getFollowingCount() {
		return following_count;
	}

	public void setFollowerCount(int follower_count) {
		this.follower_count = follower_count;
	}

	public int getFollowerCount() {
		return follower_count;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowed(boolean followed) {
		this.followed = followed;
	}

	public boolean isFollowed() {
		return followed;
	}

	public void setExternalAccount(UserExternalAccount external_account) {
		this.external_account = external_account;
	}

	public UserExternalAccount getExternalAccount() {
		return external_account;
	}

	public void setMileage(UserMileage mileage) {
		this.mileage = mileage;
	}

	public UserMileage getMileage() {
		return mileage;
	}

	public String getToken() {
		// TODO Auto-generated method stub
		return null;
	}
}