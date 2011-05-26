package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreUrl extends MatjiData{
	private int id;
	private int user_id;
	private int store_id;
	private String url;
	private Store store;
	private User user;

	public StoreUrl() {}
	
	public StoreUrl(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<StoreUrl> CREATOR = new Parcelable.Creator<StoreUrl>() {
		public StoreUrl createFromParcel(Parcel in) {
			return new StoreUrl(in);
		}

		public StoreUrl[] newArray(int size) {
			return new StoreUrl[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeInt(store_id);
		dest.writeString(url);
		dest.writeValue(store);
		dest.writeValue(user);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		store_id = in.readInt();
		url = in.readString();
		store = Store.class.cast(in.readValue(Store.class.getClassLoader()));
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
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
	public int getUser_id() {
		return user_id;
	}
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}