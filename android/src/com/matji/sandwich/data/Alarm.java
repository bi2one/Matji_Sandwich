package com.matji.sandwich.data;

public class Alarm extends MatjiData{
	private String id;
	private String received_user_id;
	private String sent_user_id;
	private String alarm_type;
	private User user;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setReceivedUserId(String received_user_id) {
		this.received_user_id = received_user_id;
	}
	public String getReceivedUserId() {
		return received_user_id;
	}
	public void setSentUserId(String sent_user_id) {
		this.sent_user_id = sent_user_id;
	}
	public String getSentUserId() {
		return sent_user_id;
	}
	public void setAlarmType(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	public String getAlarmType() {
		return alarm_type;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}

}
