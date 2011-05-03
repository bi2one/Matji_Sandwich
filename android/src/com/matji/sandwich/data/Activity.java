package com.matji.sandwich.data;

public class Activity extends MatjiData{
	private int id;
	private String user_name;
	private String user_id;
	private String object_type;
	private String object_name;
	private String object_id;
	private String object_complement_type;
	private String object_complement_name;
	private String object_complement_id;
	private String action;
	private int sequence;
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}
	public String getObject_type() {
		return object_type;
	}
	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}
	public String getObject_name() {
		return object_name;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_complement_type(String object_complement_type) {
		this.object_complement_type = object_complement_type;
	}
	public String getObject_complement_type() {
		return object_complement_type;
	}
	public void setObject_complement_name(String object_complement_name) {
		this.object_complement_name = object_complement_name;
	}
	public String getObject_complement_name() {
		return object_complement_name;
	}
	public void setObject_complement_id(String object_complement_id) {
		this.object_complement_id = object_complement_id;
	}
	public String getObject_complement_id() {
		return object_complement_id;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAction() {
		return action;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
}
