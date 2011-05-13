package com.matji.sandwich.data;

public class AttachFile extends MatjiData{
	private int id;
	private int user_id;
	private int store_id;
	private int post_id;
	private String filename;
	private String fullpath;
	private User user;
	private Store store;
	private Post post;
	private String created_at;
	private String updated_at;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public int getUserId() {
		return user_id;
	}
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStoreId() {
		return store_id;
	}
	public void setPostId(int post_id) {
		this.post_id = post_id;
	}
	public int getPostId() {
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
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	public String getCreatedAt() {
		return created_at;
	}
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getUpdatedAt() {
		return updated_at;
	}
}
