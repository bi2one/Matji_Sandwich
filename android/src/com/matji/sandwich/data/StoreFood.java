package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreFood extends MatjiData{
	private int id;
	private int user_id;
	private int food_id;
	private int store_id;
	private int like_count;
	private boolean blind;
	private Store store;
	private Food food;
	private User user;

	public StoreFood() {
		
	}
	
	public StoreFood(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<StoreFood> CREATOR = new Parcelable.Creator<StoreFood>() {
		public StoreFood createFromParcel(Parcel in) {
			return new StoreFood(in);
		}

		public StoreFood[] newArray(int size) {
			return new StoreFood[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeInt(food_id);
		dest.writeInt(store_id);
		dest.writeInt(like_count);
		dest.writeInt(blind ? 1 : 0);
		dest.writeValue(store);
		dest.writeValue(food);
		dest.writeValue(user);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		food_id = in.readInt();
		store_id = in.readInt();
		like_count = in.readInt();
		blind = in.readInt() != 0;
		store = Store.class.cast(in.readValue(Store.class.getClassLoader()));
		food = Food.class.cast(in.readValue(Food.class.getClassLoader()));
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
	public void setFoodId(int food_id) {
		this.food_id = food_id;
	}
	public int getFoodId() {
		return food_id;
	}
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStoreId() {
		return store_id;
	}
	public void setLikeCount(int like_count) {
		this.like_count = like_count;
	}
	public int getLikeCount() {
		return like_count;
	}
	public void setBlind(boolean blind) {
		this.blind = blind;
	}
	public boolean getBlind() {
		return blind;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public Food getFood() {
		return food;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	
}