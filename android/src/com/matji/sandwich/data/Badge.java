package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Badge extends MatjiData{
    private int new_notice_count;
	private int new_alarm_count;

	public Badge() {}

	public Badge(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Badge> CREATOR = new Parcelable.Creator<Badge>() {
		public Badge createFromParcel(Parcel in) {
			return new Badge(in);
		}

		public Badge[] newArray(int size) {
			return new Badge[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
	    dest.writeInt(new_notice_count);
	    dest.writeInt(new_alarm_count);
	}

	private void readFromParcel(Parcel in) {
        new_notice_count = in.readInt();
        new_alarm_count = in.readInt();
	}

    public void setNewNoticeCount(int new_notice_count) {
        this.new_notice_count = new_notice_count;
    }

    public int getNewNoticeCount() {
        return new_notice_count;
    }

    public void setNewAlarmCount(int new_alarm_count) {
        this.new_alarm_count = new_alarm_count;
    }

    public int getNewAlarmCount() {
        return new_alarm_count;
    }
}