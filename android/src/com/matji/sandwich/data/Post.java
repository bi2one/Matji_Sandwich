package com.matji.sandwich.data;

import java.util.ArrayList;

public class Post extends MatjiData{
	private String id;
	private String user_id;
	private String store_id;
	private String activity_id;
	private String post;
	private String image_count;
	private String like_count;
	private String comment_count;
	private String tag_count;
	private float lat;
	private float lng;
	private String from_where;	
	private String created_at;
	private String updated_at;

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	public String getUserId() {
		return user_id;
	}
	public void setStoreId(String store_id) {
		this.store_id = store_id;
	}
	public String getStoreId() {
		return store_id;
	}
	public void setActivityId(String activity_id) {
		this.activity_id = activity_id;
	}
	public String getActivityId() {
		return activity_id;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPost() {
		return post;
	}
	public void setImageCount(String image_count) {
		this.image_count = image_count;
	}
	public String getImageCount() {
		return image_count;
	}
	public void setLikeCount(String like_count) {
		this.like_count = like_count;
	}
	public String getLikeCount() {
		return like_count;
	}
	public void setCommentCount(String comment_count) {
		this.comment_count = comment_count;
	}
	public String getCommentCount() {
		return comment_count;
	}
	public void setTagCount(String tag_count) {
		this.tag_count = tag_count;
	}
	public String getTagCount() {
		return tag_count;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLat() {
		return lat;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public float getLng() {
		return lng;
	}
	public void setFromWhere(String from_where) {
		this.from_where = from_where;
	}
	public String getFromWhere() {
		return from_where;
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
