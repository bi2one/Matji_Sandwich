package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MatjiData implements Parcelable {
    public static final int UNSELECTED = 0;
    public static final int STORE = 1;
    public static final int POST = 2;
    public static final int STORE_FOOD = 3;

    public MatjiData() {}
    public MatjiData(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator<MatjiData> CREATOR = new Parcelable.Creator<MatjiData>() {
        public MatjiData createFromParcel(Parcel in) {
            return new MatjiData(in);
        }

        public MatjiData[] newArray(int size) {
            return new MatjiData[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel arg0, int arg1) {

    }

    public void readFromParcel(Parcel in) {

    }
}
