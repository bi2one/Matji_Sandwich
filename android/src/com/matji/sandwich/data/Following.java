package com.matji.sandwich.data;

public class Following extends MatjiData{
	private String created_at;
	private int following_user_id;
	private String updated_at;
	private int followed_user_id;
	private int id;
	private String intro;
	private String title;
	private String nick;
	private int following_count;
	private int follower_count;
	private int tag_count;
	private String userid;
	private int post_count;
	private int store_count;
	private User following_user;
	private User followed_user;
	
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	public String getCreatedAt() {
		return created_at;
	}
	public void setFollowingUserId(int following_user_id) {
		this.following_user_id = following_user_id;
	}
	public int getFollowingUserId() {
		return following_user_id;
	}
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getUpdatedAt() {
		return updated_at;
	}
	public void setFollowedUserId(int followed_user_id) {
		this.followed_user_id = followed_user_id;
	}
	public int  getFollowedUserId() {
		return followed_user_id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getIntro() {
		return intro;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getNick() {
		return nick;
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
	public void setTagCount(int tag_count) {
		this.tag_count = tag_count;
	}
	public int getTagCount() {
		return tag_count;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserid() {
		return userid;
	}
	public void setPostCount(int post_count) {
		this.post_count = post_count;
	}
	public int getPostCount() {
		return post_count;
	}
	public void setStoreCount(int store_count) {
		this.store_count = store_count;
	}
	public int getStoreCount() {
		return store_count;
	}
	public void setFollowingUser(User following_user) {
		this.following_user = following_user;
	}
	public User getFollowingUser() {
		return following_user;
	}
	public void setFollowedUser(User followed_user) {
		this.followed_user = followed_user;
	}
	public User getFollowedUser() {
		return followed_user;
	}
}