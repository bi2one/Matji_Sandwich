package com.matji.sandwich.data;

public class Like extends MatjiData{
	private int id;
	private int user_id;
	private String foreign_key;
	private String object;
	private User user;
	private Store store;
	private StoreFood store_food;
	private String created_at;
	private String updated_at;
	
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
	public void setForeignKey(String foreign_key) {
		this.foreign_key = foreign_key;
	}
	public String getForeignKey() {
		return foreign_key;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getObject() {
		return object;
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
	public void setStoreFood(StoreFood store_food) {
		this.store_food = store_food;
	}
	public StoreFood getStoreFood() {
		return store_food;
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
}
