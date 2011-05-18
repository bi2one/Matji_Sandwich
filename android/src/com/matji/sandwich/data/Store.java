package com.matji.sandwich.data;

import java.util.ArrayList;

public class Store extends MatjiData{
	private int id;
	private String name;
	private int reg_user_id;
	private String tel;
	private String address;
	private String add_address;
	private String website;
	private String text;
	private String cover;
	private double lat;
	private double lng;
	private int tag_count;
	private int post_count;
	private int image_count;
	private int like_count;
	private int bookmark_count;
	private AttachFile file; 
	private User user;
	private ArrayList<Tag> tags;
	
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
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLat() {
		return lat;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLng() {
		return lng;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getCover() {
		return cover;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setRegUserId(int reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public int getRegUserId() {
		return reg_user_id;
	}
	public void setTagCount(int tag_count) {
		this.tag_count = tag_count;
	}
	public int getTagCount() {
		return tag_count;
	}
	public void setPostCount(int post_count) {
		this.post_count = post_count;
	}
	public int getPostCount() {
		return post_count;
	}
	public void setImageCount(int image_count) {
		this.image_count = image_count;
	}
	public int getImageCount() {
		return image_count;
	}
	public void setLikeCount(int like_count) {
		this.like_count = like_count;
	}
	public int getLikeCount() {
		return like_count;
	}
	public void setBookmarkCount(int bookmark_count) {
		this.bookmark_count = bookmark_count;
	}
	public int getBookmarkCount() {
		return bookmark_count;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setFile(AttachFile file) {
		this.file = file;
	}
	public AttachFile getFile() {
		return file;
	}
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	public ArrayList<Tag> getTags() {
		return tags;
	}
}