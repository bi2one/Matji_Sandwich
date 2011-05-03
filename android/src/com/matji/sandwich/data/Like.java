package com.matji.sandwich.data;

public class Like extends MatjiData{
	private int id;
	private int user_id;
	private int foreign_key;
	private String object;
	private int sequence;
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
}
