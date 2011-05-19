package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreDetailInfoLog extends MatjiData{
	private int id;
	private int store_id;
	private int user_id;
	private String status;
	private Store store;
	private User user;

	public StoreDetailInfoLog() {
		
	}
	
	public StoreDetailInfoLog(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<StoreDetailInfoLog> CREATOR = new Parcelable.Creator<StoreDetailInfoLog>() {
		public StoreDetailInfoLog createFromParcel(Parcel in) {
			return new StoreDetailInfoLog(in);
		}

		public StoreDetailInfoLog[] newArray(int size) {
			return new StoreDetailInfoLog[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeInt(store_id);
		dest.writeString(status);
		dest.writeValue(store);
		dest.writeValue(user);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		store_id = in.readInt();
		status = in.readString();
		store = Store.class.cast(in.readValue(Store.class.getClassLoader()));
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStoreId() {
		return store_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public int getUserId() {
		return user_id;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
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