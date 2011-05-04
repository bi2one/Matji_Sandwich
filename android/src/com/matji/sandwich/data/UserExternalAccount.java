package com.matji.sandwich.data;

public class UserExternalAccount extends MatjiData{
	private int id;
	private int user_id;
	private String service;
	private String data;
	private int sequence;	
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
	public void setService(String service) {
		this.service = service;
	}
	public String getService() {
		return service;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return data;
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
	
}
