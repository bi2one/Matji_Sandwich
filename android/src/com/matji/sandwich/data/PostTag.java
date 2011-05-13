package com.matji.sandwich.data;

public class PostTag extends MatjiData{
	private int id;
	private int tag_id;
	private int post_id;
	private String created_at;
	private String updated_at;
	private Tag tag;
	private Post post;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTagId(int tag_id) {
		this.tag_id = tag_id;
	}
	public int getTagId() {
		return tag_id;
	}
	public void setPostId(int post_id) {
		this.post_id = post_id;
	}
	public int getPostId() {
		return post_id;
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
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public Tag getTag() {
		return tag;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Post getPost() {
		return post;
	}
}
