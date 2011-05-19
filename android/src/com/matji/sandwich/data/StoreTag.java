package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreTag extends MatjiData{
	private int id;
	private int tag_id;
	private int store_id;
	private int count;
	private String create_at;
	private String updated_at;
	private Tag tag;
	private Store store;
	
	public StoreTag() {
		
	}
	
	public StoreTag(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<StoreTag> CREATOR = new Parcelable.Creator<StoreTag>() {
		public StoreTag createFromParcel(Parcel in) {
			return new StoreTag(in);
		}

		public StoreTag[] newArray(int size) {
			return new StoreTag[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(tag_id);
		dest.writeInt(store_id);
		dest.writeInt(count);
		dest.writeString(create_at);
		dest.writeString(updated_at);
		dest.writeValue(tag);
		dest.writeValue(store);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		tag_id = in.readInt();
		store_id = in.readInt();
		count = in.readInt();
		create_at = in.readString();
		updated_at = in.readString();
		tag = Tag.class.cast(in.readValue(Tag.class.getClassLoader()));
		store = Store.class.cast(in.readValue(Store.class.getClassLoader()));
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
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStoreId() {
		return store_id;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount() {
		return count;
	}
	public void setCreatedAt(String create_at) {
		this.create_at = create_at;
	}
	public String getCreateAt() {
		return create_at;
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
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}
}