package com.matji.sandwich.data;

public class UserMileage extends MatjiData{
	private int id;
	private int user_id;
	private int total_point;
	private String grade;
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
	public void setTotalPoint(int total_point) {
		this.total_point = total_point;
	}
	public int getTotalPoint() {
		return total_point;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGrade() {
		return grade;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
