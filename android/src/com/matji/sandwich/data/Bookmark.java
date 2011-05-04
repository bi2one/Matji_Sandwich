package com.matji.sandwich.data;

public class Bookmark extends MatjiData{
	private int id;
	private int user_id;
	private int foreign_key;
	private String object;
	private int sequence;
	private User user;
	private Store store;
	private Region region;
	
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
	public void setForeign_key(int foreign_key) {
		this.foreign_key = foreign_key;
	}
	public int getForeign_key() {
		return foreign_key;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getObject() {
		return object;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
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
	public void setRegion(Region region) {
		this.region = region;
	}
	public Region getRegion() {
		return region;
	}
}
