package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag extends MatjiData{
	private String tag;
	private String created_at;
	private String updated_at;
	private int id;

	public Tag() {}
	
	public Tag(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
		public Tag createFromParcel(Parcel in) {
			return new Tag(in);
		}

		public Tag[] newArray(int size) {
			return new Tag[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(tag);
		dest.writeString(created_at);
		dest.writeString(updated_at);
		dest.writeInt(id);
	}

	private void readFromParcel(Parcel in) {
		tag = in.readString();
		created_at = in.readString();
		updated_at = in.readString();
		id = in.readInt();
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag.trim();
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
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
