package com.matji.sandwich.data;

import java.util.ArrayList;

public class Tag extends MatjiData{
	private int id;
	private String tag;
	private int sequence;
	private ArrayList<StoreTag> store_tags;
	private ArrayList<PostTag> post_tags;
	private ArrayList<UserTag> user_tags;

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setStore_tags(ArrayList<StoreTag> store_tags) {
		this.store_tags = store_tags;
	}
	public ArrayList<StoreTag> getStore_tags() {
		return store_tags;
	}
	public void addStoreTag(StoreTag store_tag){
		this.store_tags.add(store_tag);
	}
	public void setPost_tags(ArrayList<PostTag> post_tags) {
		this.post_tags = post_tags;
	}
	public ArrayList<PostTag> getPost_tags() {
		return post_tags;
	}
	public void addPostTag(PostTag post_tag){
		this.post_tags.add(post_tag);
	}
	public void setUser_tags(ArrayList<UserTag> user_tags) {
		this.user_tags = user_tags;
	}
	public ArrayList<UserTag> getUser_tags() {
		return user_tags;
	}
	public void addUserTag(UserTag user_tag){
		this.user_tags.add(user_tag);
	}
}
