package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Bookmark extends MatjiData{
	private int id;
	private int user_id;
	private int foreign_key;
	private String object;
	private User user;
	private Store store;
	private Region region;
	private String created_at;
	private String updated_at;
	
	public Bookmark() {
		
	}
	
	public Bookmark(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Bookmark> CREATOR = new Parcelable.Creator<Bookmark>() {
		public Bookmark createFromParcel(Parcel in) {
			return new Bookmark(in);
		}

		public Bookmark[] newArray(int size) {
			return new Bookmark[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeInt(foreign_key);
		dest.writeString(object);
		dest.writeValue(user);
		dest.writeValue(store);
		dest.writeValue(region);
		dest.writeString(created_at);
		dest.writeString(updated_at);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		foreign_key = in.readInt();
		object = in.readString();
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
		store = Store.class.cast(in.readValue(Store.class.getClassLoader()));
		region = Region.class.cast(in.readValue(Region.class.getClassLoader()));
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
	public void setForeignKey(int foreign_key) {
		this.foreign_key = foreign_key;
	}
	public int getForeignKey() {
		return foreign_key;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getObject() {
		return object;
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
	public void setRegion(Region region) {
		this.region = region;
	}
	public Region getRegion() {
		return region;
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