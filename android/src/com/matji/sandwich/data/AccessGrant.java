package com.matji.sandwich.data;

public class AccessGrant extends MatjiData{
	private int id;
	private int user_id;
	private int client_id;
	private String access_token;
	private String refresh_token;
	private String code;
	private int sequence;
	private User user;
	private Client client;
	
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
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
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
	public void setClient(Client client) {
		this.client = client;
	}
	public Client getClient() {
		return client;
	}
}
