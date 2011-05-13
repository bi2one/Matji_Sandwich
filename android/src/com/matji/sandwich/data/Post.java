package com.matji.sandwich.data;

public class Post extends MatjiData{
	private int id;
	private int user_id;
	private int store_id;
	private int activity_id;
	private String post;
	private int image_count;
	private int like_count;
	private int comment_count;
	private int tag_count;
	private float lat;
	private float lng;
	private float lat_sw;
	private float lat_ne;
	private float lng_sw;
	private float lng_ne;
	private String from_where;	
	private String created_at;
	private String updated_at;
	private User user;
	private Store store;
	private Activity activity;
	
	public void setPost(String post) {
		this.post = post;
	}
	public String getPost() {
		return post;
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
	public void setLatSw(float lat_sw) {
		this.lat_sw = lat_sw;
	}
	public float getLatSw() {
		return lat_sw;
	}
	public void setLatNe(float lat_ne) {
		this.lat_ne = lat_ne;
	}
	public float getLatNe() {
		return lat_ne;
	}
	public void setLngSw(float lng_sw) {
		this.lng_sw = lng_sw;
	}
	public float getLngSw() {
		return lng_sw;
	}
	public void setLngNe(float lng_ne) {
		this.lng_ne = lng_ne;
	}
	public float getLngNe() {
		return lng_ne;
	}
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
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public int getActivity_id() {
		return activity_id;
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
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public Activity getActivity() {
		return activity;
	}
}

