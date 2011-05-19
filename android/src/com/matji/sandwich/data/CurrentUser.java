package com.matji.sandwich.data;

public class CurrentUser extends User{
	private String token;
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
		
}
