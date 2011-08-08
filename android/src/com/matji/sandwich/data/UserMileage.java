package com.matji.sandwich.data;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

public class UserMileage extends MatjiData implements Serializable {
	private static final long serialVersionUID = 3938226042478924177L;
	private int id;
	private int user_id;
	private int total_point;
	private String grade;
	private User user;

	public UserMileage() {}
	
	public UserMileage(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<UserMileage> CREATOR = new Parcelable.Creator<UserMileage>() {
		public UserMileage createFromParcel(Parcel in) {
			return new UserMileage(in);
		}

		public UserMileage[] newArray(int size) {
			return new UserMileage[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeInt(total_point);
		dest.writeString(grade);
		dest.writeValue(user);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		total_point = in.readInt();
		grade =	in.readString(); 
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
	public void setTotalPoint(int total_point) {
		this.total_point = total_point;
	}
	public int getTotalPoint() {
		return total_point;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGrade() {
		return grade;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}