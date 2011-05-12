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
	private ArrayList<Activity> activities;
	private ArrayList<AttachFile> attach_files;
	private ArrayList<Comment> comments;
	private ArrayList<Like> likes;
	private ArrayList<PostTag> post_tags;
	private ArrayList<Tag> tags;
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
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	public ArrayList<Comment> getComments() {
		return comments;
	}
	public void addComment(Comment comment){
		this.comments.add(comment);
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
	public void setPost_tags(ArrayList<PostTag> post_tags) {
		this.post_tags = post_tags;
	}
	public ArrayList<PostTag> getPost_tags() {
		return post_tags;
	}
	public void addPostTag(PostTag post_tag){
		this.post_tags.add(post_tag);
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
