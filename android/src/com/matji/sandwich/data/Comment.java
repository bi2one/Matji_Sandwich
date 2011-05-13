package com.matji.sandwich.data;

public class Comment extends MatjiData{
	private String comment;
	private String created_at;
	private String updated_at;
	private int post_id;
	private int id;
	private int user_id;
	private User user;
	private Post post;
	private String from_where;
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
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

	public void setPostId(int post_id) {
		this.post_id = post_id;
	}

	public int getPostId() {
		return post_id;
	}

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

	public void setFromWhere(String from_where) {
		this.from_where = from_where;
	}

	public String getFromWhere() {
		return from_where;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Post getPost() {
		return post;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
