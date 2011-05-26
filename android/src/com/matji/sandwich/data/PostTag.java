package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PostTag extends MatjiData{
	private int id;
	private int tag_id;
	private int post_id;
	private String created_at;
	private String updated_at;
	private Tag tag;
	private Post post;
	
	public PostTag() {}
	
	public PostTag(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<PostTag> CREATOR = new Parcelable.Creator<PostTag>() {
		public PostTag createFromParcel(Parcel in) {
			return new PostTag(in);
		}

		public PostTag[] newArray(int size) {
			return new PostTag[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(tag_id);
		dest.writeInt(post_id);
		dest.writeString(created_at);
		dest.writeString(updated_at);
		dest.writeValue(tag);
		dest.writeValue(post);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		tag_id = in.readInt();
		post_id = in.readInt();
		created_at = in.readString();
		updated_at = in.readString();
		tag = Tag.class.cast(in.readValue(Tag.class.getClassLoader()));
		post = Post.class.cast(in.readValue(Post.class.getClassLoader()));
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTagId(int tag_id) {
		this.tag_id = tag_id;
	}
	public int getTagId() {
		return tag_id;
	}
	public void setPostId(int post_id) {
		this.post_id = post_id;
	}
	public int getPostId() {
		return post_id;
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
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public Tag getTag() {
		return tag;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Post getPost() {
		return post;
	}
}
