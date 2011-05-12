package com.matji.sandwich.data;

import java.util.ArrayList;

public class Message extends MatjiData{
	private String id;
	private String sent_user_id;
	private String received_user_id;
	private String message;
	private String sent_userid;
	private String sent_user_nick;
	private ArrayList<MatjiData> sent_user;
	
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
	public void setSentUsers(ArrayList<MatjiData> sent_user) {
		this.sent_user = sent_user;
	}
	public ArrayList<MatjiData> getSentUsers() {
		return sent_user;
	}
	public void setSentUserid(String sent_userid) {
		this.sent_userid = sent_userid;
	}
	public String getSentUserid() {
		return sent_userid;
	}
	public void setSentUserNick(String sent_user_nick) {
		this.sent_user_nick = sent_user_nick;
	}
	public String getSentUserNick() {
		return sent_user_nick;
	}
}
