package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class AttachFile extends MatjiData{
	private int id;
	private int user_id;
	private int store_id;
	private int post_id;
	private String filename;
	private String fullpath;
	private User user;
	private Store store;
	private Post post;
	private String created_at;
	private String updated_at;
	
	public AttachFile() {}
	
	public AttachFile(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<AttachFile> CREATOR = new Parcelable.Creator<AttachFile>() {
		public AttachFile createFromParcel(Parcel in) {
			return new AttachFile(in);
		}

		public AttachFile[] newArray(int size) {
			return new AttachFile[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeInt(store_id);
		dest.writeInt(post_id);
		dest.writeString(filename);
		dest.writeString(fullpath);
		dest.writeValue(user);
		dest.writeValue(store);
		dest.writeValue(post);
		dest.writeString(created_at);
		dest.writeString(updated_at);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		store_id= in.readInt();
		post_id= in.readInt();
		filename = in.readString();
		fullpath = in.readString();
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
		store = Store.class.cast(in.readValue(Store.class.getClassLoader()));
		post = Post.class.cast(in.readValue(Post.class.getClassLoader()));
		created_at = in.readString();
		updated_at = in.readString();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public int getUserId() {
		return user_id;
	}
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStoreId() {
		return store_id;
	}
	public void setPostId(int post_id) {
		this.post_id = post_id;
	}
	public int getPostId() {
		return post_id;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilename() {
		return filename;
	}
	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}
	public String getFullpath() {
		return fullpath;
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
	public void setPost(Post post) {
		this.post = post;
	}
	public Post getPost() {
		return post;
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
