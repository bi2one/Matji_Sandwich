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
	private float lat;
	private float lng;
	private int tag_count;
	private int post_count;
	private int image_count;
	private int like_count;
	private int bookmark_count;
	private int sequence;
	private ArrayList<Activity> activities;
	private ArrayList<AttachFile> attach_files;
	private ArrayList<Bookmark> bookmarks;
	private ArrayList<Like> likes;
	private ArrayList<Post> posts;
	private ArrayList<StoreFood> store_foods;
	private ArrayList<StoreTag> store_tags;
	private ArrayList<Tag> tags;
	private ArrayList<Url> urls;
	private User user;
	
	
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
	public void setReg_user_id(int reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public int getReg_user_id() {
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
	public void setAdd_address(String add_address) {
		this.add_address = add_address;
	}
	public String getAdd_address() {
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
	public void setTag_count(int tag_count) {
		this.tag_count = tag_count;
	}
	public int getTag_count() {
		return tag_count;
	}
	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}
	public int getPost_count() {
		return post_count;
	}
	public void setImage_count(int image_count) {
		this.image_count = image_count;
	}
	public int getImage_count() {
		return image_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setBookmark_count(int bookmark_count) {
		this.bookmark_count = bookmark_count;
	}
	public int getBookmark_count() {
		return bookmark_count;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}
	public ArrayList<Activity> getActivities() {
		return activities;
	}
	public void addActivity(Activity activity){
		this.activities.add(activity);
	}
	public void setAttach_files(ArrayList<AttachFile> attach_files) {
		this.attach_files = attach_files;
	}
	public ArrayList<AttachFile> getAttach_files() {
		return attach_files;
	}
	public void addAttachFile(AttachFile attach_file){
		this.attach_files.add(attach_file);
	}
	public void setLikes(ArrayList<Like> likes) {
		this.likes = likes;
	}
	public ArrayList<Like> getLikes() {
		return likes;
	}
	public void addLike(Like like){
		this.likes.add(like);
	}
	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
	public ArrayList<Post> getPosts() {
		return posts;
	}
	public void addPost(Post post){
		this.posts.add(post);
	}
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	public ArrayList<Tag> getTags() {
		return tags;
	}
	public void addTag(Tag tag){
		this.tags.add(tag);
	}
	public void setBookmarks(ArrayList<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}
	public ArrayList<Bookmark> getBookmarks() {
		return bookmarks;
	}
	public void addBookmark(Bookmark bookmark){
		this.bookmarks.add(bookmark);
	}
	public void setStore_foods(ArrayList<StoreFood> store_foods) {
		this.store_foods = store_foods;
	}
	public ArrayList<StoreFood> getStore_foods() {
		return store_foods;
	}
	public void addStoreFood(StoreFood store_food){
		this.store_foods.add(store_food);
	}
	public void setStore_tags(ArrayList<StoreTag> store_tags) {
		this.store_tags = store_tags;
	}
	public ArrayList<StoreTag> getStore_tags() {
		return store_tags;
	}
	public void addStoreTag(StoreTag store_tag){
		this.store_tags.add(store_tag);
	}
	public void setUrls(ArrayList<Url> urls) {
		this.urls = urls;
	}
	public ArrayList<Url> getUrls() {
		return urls;
	}
	public void addUrl(Url url){
		this.urls.add(url);
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
