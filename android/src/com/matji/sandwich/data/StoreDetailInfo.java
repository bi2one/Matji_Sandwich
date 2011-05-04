package com.matji.sandwich.data;

public class StoreDetailInfo extends MatjiData{
	private int id;
	private int user_id;
	private int store_id;
	private String note;
	private int sequence;
	private Store store;
	private User user;
	
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
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote() {
		return note;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	
}
