package com.matji.sandwich.data;

public class Following extends MatjiData{
	private String created_at;
	private String sequence;
	private String following_user_id;
	private String updated_at;
	private String followed_user_id;
	private String id;
	
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	
	public String getCreatedAt() {
		return created_at;
	}
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public String getSequence() {
		return sequence;
	}
	
	public void setFollowingUserId(String following_user_id) {
		this.following_user_id = following_user_id;
	}
	
	public String getFollowingUserId() {
		return following_user_id;
	}

	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	
	public String getUpdatedAt() {
		return updated_at;
	}

	public void setFollowedUserId(String followed_user_id) {
		this.followed_user_id = followed_user_id;
	}
	
	public String getFollowedUserId() {
		return followed_user_id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}