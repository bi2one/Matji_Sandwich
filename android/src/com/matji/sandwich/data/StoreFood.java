package com.matji.sandwich.data;

public class StoreFood extends MatjiData{
	private int id;
	private int user_id;
	private int food_id;
	private int store_id;
	private int like_count;
	private int blind;
	private Store store;
	private Food food;
	private User user;
	
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
	public void setFoodId(int food_id) {
		this.food_id = food_id;
	}
	public int getFoodId() {
		return food_id;
	}
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStoreId() {
		return store_id;
	}
	public void setLikeCount(int like_count) {
		this.like_count = like_count;
	}
	public int getLikeCount() {
		return like_count;
	}
	public void setBlind(int blind) {
		this.blind = blind;
	}
	public int getBlind() {
		return blind;
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
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	
}
