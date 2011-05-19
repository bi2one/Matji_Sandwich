package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Notice extends MatjiData{
	private String start_date;
	private String created_at;
	private String updated_at;
	private String subject;
	private int id;
	private String content;
	private String target;
	private String end_date;
	
	public Notice() {
		
	}
	
	public Notice(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Notice> CREATOR = new Parcelable.Creator<Notice>() {
		public Notice createFromParcel(Parcel in) {
			return new Notice(in);
		}

		public Notice[] newArray(int size) {
			return new Notice[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(start_date);
		dest.writeString(created_at);
		dest.writeString(updated_at);
		dest.writeString(subject);
		dest.writeInt(id);
		dest.writeString(content);
		dest.writeString(target);
		dest.writeString(end_date);
	}

	private void readFromParcel(Parcel in) {
		start_date = in.readString();
		created_at = in.readString();
		updated_at = in.readString();
		subject = in.readString();	
		id = in.readInt();
		content = in.readString();
		target = in.readString();
		end_date = in.readString();
	}
	
	public void setStartDate(String start_date) {
		this.start_date = start_date;
	}
	
	public String getStartDate() {
		return start_date;
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
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setEndDate(String end_date) {
		this.end_date = end_date;
	}
	
	public String getEndDate() {
		return end_date;
	}
}