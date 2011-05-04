package com.matji.sandwich.data;

public class Alarm extends MatjiData{
	private int id;
	private int received_user_id;
	private int sent_user_id;
	private String alarm_type;
	private int sequence;
	private User user;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setReceived_user_id(int received_user_id) {
		this.received_user_id = received_user_id;
	}
	public int getReceived_user_id() {
		return received_user_id;
	}
	public void setSent_user_id(int sent_user_id) {
		this.sent_user_id = sent_user_id;
	}
	public int getSent_user_id() {
		return sent_user_id;
	}
	public void setAlarm_type(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	public String getAlarm_type() {
		return alarm_type;
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
