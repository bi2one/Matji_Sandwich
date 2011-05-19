package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class UserTag extends MatjiData{
	private int id;
	private int tag_id;
	private int user_id;
	private int count;
	private String created_at;
	private String updated_at;
	
	public UserTag() {
		
	}
	
	public UserTag(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<UserTag> CREATOR = new Parcelable.Creator<UserTag>() {
		public UserTag createFromParcel(Parcel in) {
			return new UserTag(in);
		}

		public UserTag[] newArray(int size) {
			return new UserTag[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(tag_id);
		dest.writeInt(user_id);
		dest.writeInt(count);
		dest.writeString(created_at);
		dest.writeString(updated_at);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		tag_id = in.readInt();
		user_id = in.readInt();
		count = in.readInt();
		created_at = in.readString();
		updated_at = in.readString();
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
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public int getUserId() {
		return user_id;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount() {
		return count;
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