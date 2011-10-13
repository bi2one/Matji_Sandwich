package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ExternalAccount extends MatjiData{
	private boolean likedTwitter;
	private boolean likedFacebook;
	
	public ExternalAccount() {}
	
	public ExternalAccount(Parcel in) {
		readFromParcel(in);
	}

    public static final Parcelable.Creator<ExternalAccount> CREATOR = new Parcelable.Creator<ExternalAccount>() {
        public ExternalAccount createFromParcel(Parcel in) {
            return new ExternalAccount(in);
        }

        public ExternalAccount[] newArray(int size) {
            return new ExternalAccount[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int arg1) {
        dest.writeInt(likedTwitter ? 1 : 0);
        dest.writeInt(likedFacebook? 1 : 0);
    }

    public void readFromParcel(Parcel in) {
        likedTwitter = in.readInt() != 0;
        likedFacebook = in.readInt() != 0;
    }
    
    public void setLinkedTwitter(boolean Likedtwitter) {
        this.likedTwitter = Likedtwitter;
    }

    public boolean isLinkedTwitter() {
        return likedTwitter;
    }

    public void setLinkedFacebook(boolean likedFacebook) {
        this.likedFacebook = likedFacebook;
    }

    public boolean isLinkedFacebook() {
        return likedFacebook;
    }
}