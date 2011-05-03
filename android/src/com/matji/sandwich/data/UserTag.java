package com.matji.sandwich.data;

public class UserTag extends MatjiData{
	private int id;
	private int tag_id;
	private int user_id;
	private int count;
	private int sequence;
	private Tag tag;
	private User user;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}
	public int getTag_id() {
		return tag_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount() {
		return count;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public Tag getTag() {
		return tag;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
