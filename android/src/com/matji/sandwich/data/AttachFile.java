package com.matji.sandwich.data;

public class AttachFile extends MatjiData{
	private String id;
	private String user_id;
	private String store_id;
	private String post_id;
	private String filename;
	private String fullpath;
	private User user;
	private Store store;
	private Post post;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	public String getUserId() {
		return user_id;
	}
	public void setStoreId(String store_id) {
		this.store_id = store_id;
	}
	public String getStoreId() {
		return store_id;
	}
	public void setPostId(String post_id) {
		this.post_id = post_id;
	}
	public String getPostId() {
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
}
