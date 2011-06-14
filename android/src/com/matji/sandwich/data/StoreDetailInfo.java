package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreDetailInfo extends MatjiData{
	private int id;
	private int user_id;
	private int store_id;
	private String created_at;
	private String updated_at;
	private String note;
	private Store store;
	private User user;
	
	public StoreDetailInfo() {}
	
	public StoreDetailInfo(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<StoreDetailInfo> CREATOR = new Parcelable.Creator<StoreDetailInfo>() {
		public StoreDetailInfo createFromParcel(Parcel in) {
			return new StoreDetailInfo(in);
		}

		public StoreDetailInfo[] newArray(int size) {
			return new StoreDetailInfo[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeInt(store_id);
		dest.writeString(created_at);
		dest.writeString(updated_at);
		dest.writeString(note);
		dest.writeValue(store);
		dest.writeValue(user);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		store_id = in.readInt();
		created_at = in.readString();
		updated_at = in.readString();
		note = in.readString();
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
	public int getUserId() {
		return user_id;
	}
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStoreId() {
		return store_id;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote() {
		return note;
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