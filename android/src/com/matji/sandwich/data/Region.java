package com.matji.sandwich.data;

public class Region extends MatjiData{
	private int id;
	private int user_id;
	private double lat_sw;
	private double lng_sw;
	private double lat_ne;
	private double lng_ne;
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
	public void setLatSw(double lat_sw) {
		this.lat_sw = lat_sw;
	}
	public double getLatSw() {
		return lat_sw;
	}
	public void setLngSw(double lng_sw) {
		this.lng_sw = lng_sw;
	}
	public double getLngSw() {
		return lng_sw;
	}
	public void setLatNe(double lat_ne) {
		this.lat_ne = lat_ne;
	}
	public double getLatNe() {
		return lat_ne;
	}
	public void setLngNe(double lng_ne) {
		this.lng_ne = lng_ne;
	}
	public double getLngNe() {
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
