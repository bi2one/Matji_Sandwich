package com.matji.sandwich.data;

public class UserMileage extends MatjiData{
	private int id;
	private int user_id;
	private int total_point;
	private String grade;
	private int special_user;
	private int blacklist_user;
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
	public void setTotal_point(int total_point) {
		this.total_point = total_point;
	}
	public int getTotal_point() {
		return total_point;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGrade() {
		return grade;
	}
	public void setSpecial_user(int special_user) {
		this.special_user = special_user;
	}
	public int getSpecial_user() {
		return special_user;
	}
	public void setBlacklist_user(int blacklist_user) {
		this.blacklist_user = blacklist_user;
	}
	public int getBlacklist_user() {
		return blacklist_user;
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
