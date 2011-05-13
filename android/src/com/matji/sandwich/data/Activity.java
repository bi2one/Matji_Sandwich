package com.matji.sandwich.data;

public class Activity extends MatjiData{
	private int id;
	private String user_name;
	private int user_id;
	private String object_type;
	private String object_name;
	private int object_id;
	private String object_complement_type;
	private String object_complement_name;
	private int object_complement_id;
	private String created_at;
	private String updated_at;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setUserName(String user_name) {
		this.user_name = user_name;
	}
	public String getUserName() {
		return user_name;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public int getUserd() {
		return user_id;
	}
	public void setObjectType(String object_type) {
		this.object_type = object_type;
	}
	public String getObjectType() {
		return object_type;
	}
	public void setObjectName(String object_name) {
		this.object_name = object_name;
	}
	public String getObjectName() {
		return object_name;
	}
	public void setObjectId(int object_id) {
		this.object_id = object_id;
	}
	public int getObjectId() {
		return object_id;
	}
	public void setObjectComplementType(String object_complement_type) {
		this.object_complement_type = object_complement_type;
	}
	public String getObjectComplementType() {
		return object_complement_type;
	}
	public void setObjectComplementName(String object_complement_name) {
		this.object_complement_name = object_complement_name;
	}
	public String getObjectComplementName() {
		return object_complement_name;
	}
	public void setObjectComplementId(int object_complement_id) {
		this.object_complement_id = object_complement_id;
	}
	public int getObjectComplementId() {
		return object_complement_id;
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
}
