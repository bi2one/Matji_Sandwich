package com.matji.sandwich.data;

import java.util.*;

public class Message extends MatjiData{
	private String id;
	private String sent_user_id;
	private String received_user_id;
	private String message;
	private ArrayList<User> sent_users;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setSentUserId(String sent_user_id) {
		this.sent_user_id = sent_user_id;
	}
	public String getSentUserId() {
		return sent_user_id;
	}
	public void setReceivedUserId(String received_user_id) {
		this.received_user_id = received_user_id;
	}
	public String getReceivedUserId() {
		return received_user_id;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setSentUsers(ArrayList<User> sent_users) {
		this.sent_users = sent_users;
	}
	public ArrayList<User> getSentUsers() {
		return sent_users;
	}
}
