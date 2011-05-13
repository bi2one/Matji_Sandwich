package com.matji.sandwich.data;

public class Alarm extends MatjiData{
	private int id;
	private int received_user_id;
	private int sent_user_id;
	private String alarm_type;
	private User sent_user;
	private User received_user;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setReceivedUserId(int received_user_id) {
		this.received_user_id = received_user_id;
	}
	public int getReceivedUserId() {
		return received_user_id;
	}
	public void setSentUserId(int sent_user_id) {
		this.sent_user_id = sent_user_id;
	}
	public int getSentUserId() {
		return sent_user_id;
	}
	public void setAlarmType(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	public String getAlarmType() {
		return alarm_type;
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

}
