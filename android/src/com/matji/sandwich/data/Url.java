package com.matji.sandwich.data;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Url extends MatjiData implements Serializable {
	private static final long serialVersionUID = 3938226042478924177L;
	private int id;
	private int user_id;
	private int store_id;
	private String url;

	public Url() {}

	public Url(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Url> CREATOR = new Parcelable.Creator<Url>() {
		public Url createFromParcel(Parcel in) {
			return new Url(in);
		}

		public Url[] newArray(int size) {
			return new Url[size];
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
	}

	public void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		store_id = in.readInt();
		url = in.readString();
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

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}