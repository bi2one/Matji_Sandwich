package com.matji.sandwich.data;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

public class AlarmSetting extends MatjiData implements Serializable {
    
    public enum AlarmSettingType {
        COMMENT,
        FOLLOWING,
        LIKEPOST,
        MESSAGE
    }
    
    private int id;
    private int user_id;
    private boolean comment;
    private boolean following;
    private boolean likepost;
    private boolean message;
	
	public AlarmSetting() {}
	
	public AlarmSetting(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<AlarmSetting> CREATOR = new Parcelable.Creator<AlarmSetting>() {
		public AlarmSetting createFromParcel(Parcel in) {
			return new AlarmSetting(in);
		}

		public AlarmSetting[] newArray(int size) {
			return new AlarmSetting[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
	    dest.writeInt(id);
	    dest.writeInt(user_id);
	    dest.writeInt((comment) ? 1 : 0);
        dest.writeInt((following) ? 1 : 0);
        dest.writeInt((likepost) ? 1 : 0);
        dest.writeInt((message) ? 1 : 0);
	}

	private void readFromParcel(Parcel in) {
	    id = in.readInt();
	    user_id = in.readInt();
        comment = in.readInt() != 0;
        following = in.readInt() != 0;
        likepost = in.readInt() != 0;
        message = in.readInt() != 0;
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

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean getComment() {
        return comment;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public boolean getFollowing() {
        return following;
    }

    public void setLikepost(boolean likepost) {
        this.likepost = likepost;
    }

    public boolean getLikepost() {
        return likepost;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public boolean getMessage() {
        return message;
    }
}
