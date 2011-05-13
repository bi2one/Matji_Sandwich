package com.matji.sandwich.data;

public class User extends MatjiData{
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
	private UserExternalAccount external_account;
	private UserMileage mileage;
	
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
}
