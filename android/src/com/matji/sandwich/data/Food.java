package com.matji.sandwich.data;

public class Food extends MatjiData{
	private int like_count;
	private String created_at;
	private String updated_at;
	private int id;
	private String name;
	
	public void setLikeCount(int like_count) {
		this.like_count = like_count;
	}
	
	public int getLikeCount() {
		return like_count;
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

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}