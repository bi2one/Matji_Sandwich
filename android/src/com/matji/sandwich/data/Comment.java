package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment extends MatjiData{
	private String comment;
	private String created_at;
	private String updated_at;
	private int post_id;
	private int id;
	private int user_id;
	private User user;
	private Post post;
	private String from_where;
	
	public Comment() {
		
	}
	
	public Comment(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
		public Comment createFromParcel(Parcel in) {
			return new Comment(in);
		}

		public Comment[] newArray(int size) {
			return new Comment[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(comment);
		dest.writeString(created_at);
		dest.writeString(updated_at);
		dest.writeInt(post_id);
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeValue(user);
		dest.writeValue(post);
		dest.writeString(from_where);
	}

	private void readFromParcel(Parcel in) {
		comment = in.readString();
		created_at = in.readString();
		updated_at = in.readString();
		post_id = in.readInt();
		id = in.readInt();
		user_id = in.readInt();
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
		post = Post.class.cast(in.readValue(Post.class.getClassLoader()));
		from_where = in.readString();
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComment() {
		return comment;
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
	public void setPostId(int post_id) {
		this.post_id = post_id;
	}
	public int getPostId() {
		return post_id;
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
	public void setFromWhere(String from_where) {
		this.from_where = from_where;
	}
	public String getFromWhere() {
		return from_where;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Post getPost() {
		return post;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}