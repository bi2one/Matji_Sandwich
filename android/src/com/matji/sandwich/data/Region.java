package com.matji.sandwich.data;

public class Region extends MatjiData{
	private int id;
	private int user_id;
	private int lat_sw;
	private int lng_sw;
	private int lat_ne;
	private int lng_ne;
	private String description;
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
	public void setLatSw(int lat_sw) {
		this.lat_sw = (int) lat_sw * 1000000;
	}
	public int getLatSw() {
		return lat_sw;
	}
	public void setLngSw(int lng_sw) {
		this.lng_sw = (int) lng_sw * 1000000;
	}
	public int getLngSw() {
		return lng_sw;
	}
	public void setLatNe(int lat_ne) {
		this.lat_ne = (int) lat_ne * 1000000;
	}
	public int getLatNe() {
		return lat_ne;
	}
	public void setLngNe(int lng_ne) {
		this.lng_ne = (int) lng_ne * 1000000;
	}
	public int getLngNe() {
		return lng_ne;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
