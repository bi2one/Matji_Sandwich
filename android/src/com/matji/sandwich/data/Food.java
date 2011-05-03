package com.matji.sandwich.data;

import java.util.ArrayList;

public class Food extends MatjiData{
	private int id;
	private String name;
	private int like_count;
	private int sequence;
	private ArrayList<StoreFood> storefoods;
	private ArrayList<Like> likes;
	
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
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setStorefoods(ArrayList<StoreFood> storefoods) {
		this.storefoods = storefoods;
	}
	public ArrayList<StoreFood> getStorefoods() {
		return storefoods;
	}
	public void addStorefoods(StoreFood storefood){
		this.storefoods.add(storefood);
	}
	public void setLikes(ArrayList<Like> likes) {
		this.likes = likes;
	}
	public ArrayList<Like> getLikes() {
		return likes;
	}
	public void addLikes(Like like){
		this.likes.add(like);
	}
}
