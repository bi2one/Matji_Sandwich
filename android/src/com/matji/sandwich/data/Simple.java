package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Simple extends MatjiData {
    private String content;

    public Simple() {}
    
	public Simple(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Simple> CREATOR = new Parcelable.Creator<Simple>() {
		public Simple createFromParcel(Parcel in) {
			return new Simple(in);
		}

		public Simple[] newArray(int size) {
			return new Simple[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(content);
	}

	public void readFromParcel(Parcel in) {
		content = in.readString();
	}
	
    public void setContent(String content) {
	this.content = content;
    }

    public String getContent() {
	return content;
    }
}