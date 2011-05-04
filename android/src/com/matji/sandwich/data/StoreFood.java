package com.matji.sandwich.data;

import java.util.ArrayList;

public class StoreFood extends MatjiData{
	private int id;
	private int user_id;
	private int food_id;
	private int store_id;
	private int like_count;
	private int blind;
	private int sequence;
	private ArrayList<Like> likes;
	private Store store;
	private Food food;
	
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
	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}
	public int getFood_id() {
		return food_id;
	}
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setBlind(int blind) {
		this.blind = blind;
	}
	public int getBlind() {
		return blind;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setLikes(ArrayList<Like> likes) {
		this.likes = likes;
	}
	public ArrayList<Like> getLikes() {
		return likes;
	}
	public void addLike(Like like){
		this.likes.add(like);
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public Food getFood() {
		return food;
	}
	
}
