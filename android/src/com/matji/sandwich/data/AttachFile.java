package com.matji.sandwich.data;

public class AttachFile extends MatjiData{
	private int id;
	private int user_id;
	private int store_id;
	private int post_id;
	private String filename;
	private String fullpath;
	private int sequence;
	private User user;
	private Store store;
	private Post post;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilename() {
		return filename;
	}
	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}
	public String getFullpath() {
		return fullpath;
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
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Post getPost() {
		return post;
	}
	
}
