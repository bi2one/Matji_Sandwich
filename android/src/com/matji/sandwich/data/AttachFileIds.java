package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class AttachFileIds extends MatjiData {
	private int[] ids;

	public AttachFileIds() {}
	
	public AttachFileIds(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<AttachFileIds> CREATOR = new Parcelable.Creator<AttachFileIds>() {
		public AttachFileIds createFromParcel(Parcel in) {
			return new AttachFileIds(in);
		}

		public AttachFileIds[] newArray(int size) {
			return new AttachFileIds[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(ids.length);
		dest.writeIntArray(ids);
	}

	private void readFromParcel(Parcel in) {
		ids = new int[in.readInt()];
		in.readIntArray(ids);
	}
	
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	
	public int[] getIds() {
		return ids;
	}	
}