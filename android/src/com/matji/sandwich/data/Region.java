package com.matji.sandwich.data;

public class Region extends MatjiData{
	private int id;
	private int user_id;
	private float lat_sw;
	private float lng_sw;
	private float lat_ne;
	private float lng_ne;
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
	public void setLatSw(float lat_sw) {
		this.lat_sw = lat_sw;
	}
	public float getLatSw() {
		return lat_sw;
	}
	public void setLngSw(float lng_sw) {
		this.lng_sw = lng_sw;
	}
	public float getLngSw() {
		return lng_sw;
	}
	public void setLatNe(float lat_ne) {
		this.lat_ne = lat_ne;
	}
	public float getLatNe() {
		return lat_ne;
	}
	public void setLngNe(float lng_ne) {
		this.lng_ne = lng_ne;
	}
	public float getLngNe() {
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
