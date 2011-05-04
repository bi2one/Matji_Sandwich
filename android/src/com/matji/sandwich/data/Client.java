package com.matji.sandwich.data;

public class Client extends MatjiData{
	private int id;
	private int user_id;
	private String application_name;
	private String client_id;
	private String client_secret;
	private String redirect_uri;
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
	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}
	public String getApplication_name() {
		return application_name;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
	public String getRedirect_uri() {
		return redirect_uri;
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
