package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class UserExternalAccount extends MatjiData{
	private int id;
	private int user_id;
	private String service;
	private String data;
	private User user;

	public UserExternalAccount() {}
	
	public UserExternalAccount(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<UserExternalAccount> CREATOR = new Parcelable.Creator<UserExternalAccount>() {
		public UserExternalAccount createFromParcel(Parcel in) {
			return new UserExternalAccount(in);
		}

		public UserExternalAccount[] newArray(int size) {
			return new UserExternalAccount[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeString(service);
		dest.writeString(data);
		dest.writeValue(user);
	}

	public void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		service = in.readString();
		data = in.readString();
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
	public void setService(String service) {
		this.service = service;
	}
	public String getService() {
		return service;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return data;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}