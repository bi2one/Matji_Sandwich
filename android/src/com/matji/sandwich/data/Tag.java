package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag extends MatjiData {
	protected int id;
	protected int tag_id;
	protected String created_at;
	protected String updated_at;
	protected int count;	
	protected SimpleTag tag;

	public Tag() {}
	
    public Tag(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int arg1) {
        dest.writeInt(id);
        dest.writeInt(tag_id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeInt(count);
        dest.writeValue(tag);
    }

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        tag_id = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        count = in.readInt();
        tag = SimpleTag.class.cast(in.readValue(SimpleTag.class.getClassLoader()));
    }
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTagId() {
		return tag_id;
	}
	
	public void setTagId(int tag_id) {
		this.tag_id = tag_id;
	}
	
	public String getCreatedAt() {
		return created_at;
	}
	
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	
	public String getUpdatedAt() {
		return updated_at;
	}
	
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public SimpleTag getTag() {
		return tag;
	}
	
	public void setTag(SimpleTag tag) {
		this.tag = tag;
	}
}
