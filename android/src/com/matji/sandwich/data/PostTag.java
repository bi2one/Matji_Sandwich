package com.matji.sandwich.data;

public class PostTag extends MatjiData{
	private int id;
	private int tag_id;
	private int post_id;
	private int sequence;
	private Post post;
	private Tag tag;
		
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
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Post getPost() {
		return post;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public Tag getTag() {
		return tag;
	}
}
