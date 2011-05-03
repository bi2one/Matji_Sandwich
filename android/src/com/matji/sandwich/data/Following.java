package com.matji.sandwich.data;

public class Following extends MatjiData{
	private int id;
	private int following_user_id;
	private int followed_user_id;
	private int sequence;
	private User user;
		
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setFollowing_user_id(int following_user_id) {
		this.following_user_id = following_user_id;
	}
	public int getFollowing_user_id() {
		return following_user_id;
	}
	public void setFollowed_user_id(int followed_user_id) {
		this.followed_user_id = followed_user_id;
	}
	public int getFollowed_user_id() {
		return followed_user_id;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
