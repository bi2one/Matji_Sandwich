package com.matji.sandwich.data;

public class Message extends MatjiData{
	private int id;
	private int sent_user_id;
	private int received_user_id;
	private int thread_id;
	private String message;
	private User sent_user;
	private User received_user;
	private String created_at;
	private String updated_at;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setSentUserId(int sent_user_id) {
		this.sent_user_id = sent_user_id;
	}
	public int getSentUserId() {
		return sent_user_id;
	}
	public void setReceivedUserId(int received_user_id) {
		this.received_user_id = received_user_id;
	}
	public int getReceivedUserId() {
		return received_user_id;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setSentUser(User sent_user) {
		this.sent_user = sent_user;
	}
	public User getSentUser() {
		return sent_user;
	}
	public void setReceivedUser(User received_user) {
		this.received_user = received_user;
	}
	public User getReceivedUser() {
		return received_user;
	}
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	public String getCreatedAt() {
		return created_at;
	}
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getUpdatedAt() {
		return updated_at;
	}
	public void setThreadId(int thread_id) {
		this.thread_id = thread_id;
	}
	public int getThreadId() {
		return thread_id;
	}
}
