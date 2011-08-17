package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MatjiData implements Parcelable {
    public static final int UNSELECTED = 0;
    public static final int STORE = 1;
    public static final int POST = 2;
    public static final int STORE_FOOD = 3;

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel arg0, int arg1) {
		
	}
}
