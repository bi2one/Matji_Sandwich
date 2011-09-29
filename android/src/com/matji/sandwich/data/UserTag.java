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
		super.writeToParcel(dest, arg1);
	    dest.writeInt(user_id);
		dest.writeValue(user);
	}

	@Override
	protected void readFromParcel(Parcel in) {
	    super.readFromParcel(in);
		user_id = in.readInt();
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
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