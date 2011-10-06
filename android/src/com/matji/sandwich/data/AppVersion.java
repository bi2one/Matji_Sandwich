package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class AppVersion extends MatjiData {
	private int id;
	private String target;
	private String version;
	private boolean urgent;
	private String url;
	
	public AppVersion() {}
	
	public AppVersion(Parcel in) {
		readFromParcel(in);
	}
	
	public static final Parcelable.Creator<AppVersion> CREATOR = new Parcelable.Creator<AppVersion>() {
		public AppVersion createFromParcel(Parcel in) {
			return new AppVersion(in);
		}
		
		public AppVersion[] newArray(int size) {
			return new AppVersion[size];
		}
	};
	
	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeString(target);
		dest.writeString(version);
		dest.writeInt(urgent ? 1 : 0);
		dest.writeString(url);
	}
	
	public void readFromParcel(Parcel in) {
		setId(in.readInt());
		setTarget(in.readString());
		setVersion(in.readString());
		setUrgent(in.readInt() != 0);
		setUrl(in.readString());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isUrgent() {
		return urgent;
	}

	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
