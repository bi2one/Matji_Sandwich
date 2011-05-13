package com.matji.sandwich.data;

import java.util.ArrayList;

public class Comment extends MatjiData{
	private String comment;
	private String created_at;
	private String sequence;
	private String updated_at;
	private String post_id;
	private String id;
	private String user_id;
	private ArrayList<MatjiData> user;
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

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getSequence() {
		return sequence;
	}
	
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getUpdatedAt() {
		return updated_at;
	}

	public void setPostId(String post_id) {
		this.post_id = post_id;
	}

	public String getPostId() {
		return post_id;
	}

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

	public void setFromWhere(String from_where) {
		this.from_where = from_where;
	}

	public String getFromWhere() {
		return from_where;
	}

	public void setUser(ArrayList<MatjiData> user) {
		this.user = user;
	}

	public ArrayList<MatjiData> getUser() {
		return user;
	}
}
