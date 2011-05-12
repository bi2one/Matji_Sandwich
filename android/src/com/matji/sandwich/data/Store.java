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
<<<<<<< HEAD
	private String like;
	private String bookmark;
	private String note;
	private String object;
	private ArrayList<User> users;
	
=======
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
	
	
>>>>>>> 88467ab63d62393296f800f10d9c821b9818c76b
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
<<<<<<< HEAD
	public void setRegUserId(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public String getRegUserId() {
=======
	public void setReg_user_id(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public String getReg_user_id() {
>>>>>>> 88467ab63d62393296f800f10d9c821b9818c76b
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
<<<<<<< HEAD
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
=======
	public void setTag_count(String tag_count) {
		this.tag_count = tag_count;
	}
	public String getTag_count() {
		return tag_count;
	}
	public void setPost_count(String post_count) {
		this.post_count = post_count;
	}
	public String getPost_count() {
		return post_count;
	}
	public void setImage_count(String image_count) {
		this.image_count = image_count;
	}
	public String getImage_count() {
		return image_count;
	}
	public void setLike_count(String like_count) {
		this.like_count = like_count;
	}
	public String getLike_count() {
		return like_count;
	}
	public void setBookmark_count(String bookmark_count) {
		this.bookmark_count = bookmark_count;
	}
	public String getBookmark_count() {
		return bookmark_count;
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
>>>>>>> 88467ab63d62393296f800f10d9c821b9818c76b
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
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
}
