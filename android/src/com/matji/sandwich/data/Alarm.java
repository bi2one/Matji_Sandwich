package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm extends MatjiData{
	private int id;
	private int received_user_id;
	private int sent_user_id;
	private String alarm_type;
	private User sent_user;
	private User received_user;
	private String created_at;
	private String updated_at;

	public Alarm() {
		
	}
	
	public Alarm(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Alarm> CREATOR = new Parcelable.Creator<Alarm>() {
		public Alarm createFromParcel(Parcel in) {
			return new Alarm(in);
		}

		public Alarm[] newArray(int size) {
			return new Alarm[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(received_user_id);
		dest.writeInt(sent_user_id);
		dest.writeString(alarm_type);
		dest.writeValue(sent_user);
		dest.writeValue(received_user);
		dest.writeString(created_at);
		dest.writeString(updated_at);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		received_user_id = in.readInt();
		sent_user_id = in.readInt();
		alarm_type = in.readString();
		sent_user = User.class.cast(in.readValue(User.class.getClassLoader()));
		received_user = User.class.cast(in.readValue(User.class.getClassLoader()));
		created_at = in.readString();
		updated_at = in.readString();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setReceivedUserId(int received_user_id) {
		this.received_user_id = received_user_id;
	}
	public int getReceivedUserId() {
		return received_user_id;
	}
	public void setSentUserId(int sent_user_id) {
		this.sent_user_id = sent_user_id;
	}
	public int getSentUserId() {
		return sent_user_id;
	}
	public void setAlarmType(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	public String getAlarmType() {
		return alarm_type;
	}
	public void setSentUser(User sent_user) {
		this.sent_user = sent_user;
	}
	public User getSentUser() {
		return sent_user;
	}
	public void setReceivedUser(User received_user) {
		this.received_user = received_user;
	}
	public User getReceivedUser() {
		return received_user;
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
