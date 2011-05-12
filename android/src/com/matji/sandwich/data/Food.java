package com.matji.sandwich.data;

import java.util.ArrayList;

public class Food extends MatjiData{
	private String like_count;
	private String created_at;
	private String sequence;
	private String blind;
	private String updated_at;
	private String id;
	private String store_id;
	private ArrayList<MatjiData> store;
	private String like;
	private String user_id;
	private String food_id;
	private String name;
	
	public void setLikeCount(String like_count) {
		this.like_count = like_count;
	}
	
	public String getLikeCount() {
		return like_count;
	}

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

	public void setBlind(String blind) {
		this.blind = blind;
	}

	public String getBlind() {
		return blind;
	}	
	
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getUpdatedAt() {
		return updated_at;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setStoreId(String store_id) {
		this.store_id = store_id;
	}

	public String getStoreId() {
		return store_id;
	}

	public void setStore(ArrayList<MatjiData> store) {
		this.store = store;
	}

	public ArrayList<MatjiData> getStore() {
		return store;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getLike() {
		return like;
	}

	public void setUserId(String user_id) {
		this.user_id = user_id;
	}

	public String getUserId() {
		return user_id;
	}

	public void setFoodId(String food_id) {
		this.food_id = food_id;
	}

	public String getFoodId() {
		return food_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}