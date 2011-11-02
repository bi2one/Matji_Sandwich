package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Email extends MatjiData{
	private String email;
	
	public Email() {}
	
	public Email(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Email> CREATOR = new Parcelable.Creator<Email>() {
		public Email createFromParcel(Parcel in) {
			return new Email(in);
		}

		public Email[] newArray(int size) {
			return new Email[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
	    dest.writeString(email);
	}

	public void readFromParcel(Parcel in) {
		email = in.readString();
	}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString() {
        return email;
    }
}
