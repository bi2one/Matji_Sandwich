package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Region extends MatjiData{
	private int id;
	private int user_id;
	private int lat_sw;
	private int lng_sw;
	private int lat_ne;
	private int lng_ne;
	private String description;
	private User user;
	
	public Region() {
		
	}
	
	public Region(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Region> CREATOR = new Parcelable.Creator<Region>() {
		public Region createFromParcel(Parcel in) {
			return new Region(in);
		}

		public Region[] newArray(int size) {
			return new Region[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeInt(lat_sw);
		dest.writeInt(lng_sw);
		dest.writeInt(lat_ne);
		dest.writeInt(lng_ne);
		dest.writeString(description);
		dest.writeValue(user);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		lat_sw = in.readInt();
		lng_sw = in.readInt();
		lat_ne = in.readInt();
		lng_ne = in.readInt();
		description = in.readString();
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
	public void setLatSw(double lat_sw) {
		this.lat_sw = (int) (lat_sw * 1E6);
	}
	public int getLatSw() {
		return lat_sw;
	}
	public void setLngSw(double lng_sw) {
		this.lng_sw = (int) (lng_sw * 1E6);
	}
	public int getLngSw() {
		return lng_sw;
	}
	public void setLatNe(double lat_ne) {
		this.lat_ne = (int) (lat_ne * 1E6);
	}
	public int getLatNe() {
		return lat_ne;
	}
	public void setLngNe(double lng_ne) {
		this.lng_ne = (int) (lng_ne * 1E6);
	}
	public int getLngNe() {
		return lng_ne;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
