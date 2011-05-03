package com.matji.sandwich.data;

public class Region extends MatjiData{
	private int id;
	private int user_id;
	private float lat_sw;
	private float lng_sw;
	private float lat_ne;
	private float lng_ne;
	private String description;
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
	public void setLat_sw(float lat_sw) {
		this.lat_sw = lat_sw;
	}
	public float getLat_sw() {
		return lat_sw;
	}
	public void setLng_sw(float lng_sw) {
		this.lng_sw = lng_sw;
	}
	public float getLng_sw() {
		return lng_sw;
	}
	public void setLat_ne(float lat_ne) {
		this.lat_ne = lat_ne;
	}
	public float getLat_ne() {
		return lat_ne;
	}
	public void setLng_ne(float lng_ne) {
		this.lng_ne = lng_ne;
	}
	public float getLng_ne() {
		return lng_ne;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
}
