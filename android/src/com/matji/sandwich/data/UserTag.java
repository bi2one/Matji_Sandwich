package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class UserTag extends Tag {
	private int user_id;
	private User user;
	
	public UserTag() {
	    super();
	}
	
	public UserTag(Parcel in) {
		super(in);
	}

	public static final Parcelable.Creator<UserTag> CREATOR = new Parcelable.Creator<UserTag>() {
		public UserTag createFromParcel(Parcel in) {
			return new UserTag(in);
		}

		public UserTag[] newArray(int size) {
			return new UserTag[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(tag_id);
		dest.writeInt(user_id);
		dest.writeInt(count);
		dest.writeString(created_at);
		dest.writeString(updated_at);
	}

	@Override
	protected void readFromParcel(Parcel in) {
		id = in.readInt();
		tag_id = in.readInt();
		user_id = in.readInt();
		count = in.readInt();
		created_at = in.readString();
		updated_at = in.readString();
	}
	
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	public int getUserId() {
		return user_id;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getStore() {
		return user;
	}
}