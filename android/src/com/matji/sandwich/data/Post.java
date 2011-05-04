package com.matji.sandwich.data;

import java.util.ArrayList;

public class Post extends MatjiData{
	private int id;
	private int user_id;
	private int stroe_id;
	private int activity_id;
	private String post;
	private int image_count;
	private int like_count;
	private int comment_count;
	private int tag_count;
	private float lat;
	private float lng;
	private String from_where;
	private int sequence;
	private ArrayList<Activity> activities;
	private ArrayList<AttachFile> attach_files;
	private ArrayList<Comment> comments;
	private ArrayList<Like> likes;
	private ArrayList<PostTag> post_tags;
	private ArrayList<Tag> tags;
		
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setStroe_id(int stroe_id) {
		this.stroe_id = stroe_id;
	}
	public int getStroe_id() {
		return stroe_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public int getActivity_id() {
		return activity_id;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPost() {
		return post;
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
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setTag_count(int tag_count) {
		this.tag_count = tag_count;
	}
	public int getTag_count() {
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
	public void setFrom_where(String from_where) {
		this.from_where = from_where;
	}
	public String getFrom_where() {
		return from_where;
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
}
