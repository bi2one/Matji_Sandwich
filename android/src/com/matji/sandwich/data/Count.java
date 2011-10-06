package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Count extends MatjiData{
	private int count;
	
	public Count() {
		this.count = 0;
	}
	
	public Count(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Count> CREATOR = new Parcelable.Creator<Count>() {
		public Count createFromParcel(Parcel in) {
			return new Count(in);
		}

		public Count[] newArray(int size) {
			return new Count[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(count);
	}

	public void readFromParcel(Parcel in) {
		count = in.readInt();
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}
}
