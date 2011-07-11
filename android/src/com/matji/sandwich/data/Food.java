package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Food extends MatjiData{
	private String created_at;
	private String updated_at;
	private int id;
	private String name;

	public Food() {}
	
	public Food(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
		public Food createFromParcel(Parcel in) {
			return new Food(in);
		}

		public Food[] newArray(int size) {
			return new Food[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(created_at);
		dest.writeString(updated_at);
		dest.writeInt(id);
		dest.writeString(name);
	}

	private void readFromParcel(Parcel in) {
		created_at = in.readString();	
		updated_at = in.readString();
		id = in.readInt();
		name = in.readString();
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}