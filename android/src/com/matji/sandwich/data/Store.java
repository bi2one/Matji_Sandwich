package com.matji.sandwich.data;

import java.util.ArrayList;

public class Store extends MatjiData{
	private String id;
	private String name;
	private String reg_user_id;
	private String tel;
	private String address;
	private String add_address;
	private String website;
	private String text;
	private String cover;
	private float lat;
	private float lng;
	private String tag_count;
	private String post_count;
	private String image_count;
	private String like_count;
	private String bookmark_count;
	private String like;
	private String bookmark;
	private String note;
	private String object;
	private ArrayList<MatjiData> user;

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setRegUserId(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public String getRegUserId() {
		return reg_user_id;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTel() {
		return tel;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
	public void setAddAddress(String add_address) {
		this.add_address = add_address;
	}
	public String getAddAddress() {
		return add_address;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getWebsite() {
		return website;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
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
	public void setTagCount(String tag_count) {
		this.tag_count = tag_count;
	}
	public String getTagCount() {
		return tag_count;
	}
	public void setPostCount(String post_count) {
		this.post_count = post_count;
	}
	public String getPostCount() {
		return post_count;
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
	public void setBookmarkCount(String bookmark_count) {
		this.bookmark_count = bookmark_count;
	}
	public String getBookmarkCount() {
		return bookmark_count;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getCover() {
		return cover;
	}
	public void setLike(String like) {
		this.like = like;
	}
	public String getLike() {
		return like;
	}
	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
	public String getBookmark() {
		return bookmark;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote() {
		return note;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getObject() {
		return object;
	}
	public void setUser(ArrayList<MatjiData> user) {
		this.user = user;
	}
	public ArrayList<MatjiData> getUser() {
		return user;
	}
}
