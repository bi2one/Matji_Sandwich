package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Like extends MatjiData{
	private int id;
	private int user_id;
	private String foreign_key;
	private String object;
	private User user;
	private Store store;
	private StoreFood store_food;
	private String created_at;
	private String updated_at;

	public Like() {
		
	}
	
	public Like(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Like> CREATOR = new Parcelable.Creator<Like>() {
		public Like createFromParcel(Parcel in) {
			return new Like(in);
		}

		public Like[] newArray(int size) {
			return new Like[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeString(foreign_key);
		dest.writeString(object);
		dest.writeValue(user);
		dest.writeValue(store);
		dest.writeValue(store_food);
		dest.writeString(created_at);
		dest.writeString(updated_at);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		foreign_key = in.readString();	
		object = in.readString();	
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
		store = Store.class.cast(in.readValue(Store.class.getClassLoader()));
		store_food = StoreFood.class.cast(in.readValue(StoreFood.class.getClassLoader()));
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
	public void setForeignKey(String foreign_key) {
		this.foreign_key = foreign_key;
	}
	public String getForeignKey() {
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
	public void setStoreFood(StoreFood store_food) {
		this.store_food = store_food;
	}
	public StoreFood getStoreFood() {
		return store_food;
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