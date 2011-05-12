package com.matji.sandwich.data;

public class Like extends MatjiData{
	private String id;
	private String user_id;
	private String foreign_key;
	private String object;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setForeign_key(String foreign_key) {
		this.foreign_key = foreign_key;
	}
	public String getForeign_key() {
		return foreign_key;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getObject() {
		return object;
	}
}
