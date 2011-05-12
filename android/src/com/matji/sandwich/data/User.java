package com.matji.sandwich.data;

import java.util.ArrayList;

public class User extends MatjiData{
	private String id;
	private String userid;
	private String hashed_password;
	private String old_hashed_password;
	private String salt;
	private String nick;
	private String email;
	private String title;
	private String intro;
	private String post_count;
	private String tag_count;
	private String store_count;
	private String following_count;
	private String follower_count;
	private String following;
	private String followed;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserid() {
		return userid;
	}
	public void setHashedPassword(String hashed_password) {
		this.hashed_password = hashed_password;
	}
	public String getHashedPassword() {
		return hashed_password;
	}
	public void setOld_hashedPassword(String old_hashed_password) {
		this.old_hashed_password = old_hashed_password;
	}
	public String getOld_hashedPassword() {
		return old_hashed_password;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getSalt() {
		return salt;
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
	public void setPostCount(String post_count) {
		this.post_count = post_count;
	}
	public String getPostCount() {
		return post_count;
	}
	public void setTagCount(String tag_count) {
		this.tag_count = tag_count;
	}
	public String getTagCount() {
		return tag_count;
	}
	public void setStoreCount(String store_count) {
		this.store_count = store_count;
	}
	public String getStoreCount() {
		return store_count;
	}
	public void setFollowingCount(String following_count) {
		this.following_count = following_count;
	}
	public String getFollowingCount() {
		return following_count;
	}
	public void setFollowerCount(String follower_count) {
		this.follower_count = follower_count;
	}
	public String getFollowerCount() {
		return follower_count;
	}
	public void setFollowing(String following) {
		this.following = following;
	}
	public String getFollowing() {
		return following;
	}
	public void setFollowed(String followed) {
		this.followed = followed;
	}
	public String getFollowed() {
		return followed;
	}
}
