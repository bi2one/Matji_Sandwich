package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class AttachFiles extends MatjiData {
	private AttachFile[] files;

	public AttachFiles() {}
	
	public AttachFiles(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<AttachFiles> CREATOR = new Parcelable.Creator<AttachFiles>() {
		public AttachFiles createFromParcel(Parcel in) {
			return new AttachFiles(in);
		}

		public AttachFiles[] newArray(int size) {
			return new AttachFiles[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(files.length);
		dest.writeTypedArray(files, files.length);
	}

	private void readFromParcel(Parcel in) {
		files = new AttachFile[in.readInt()];
		in.readTypedArray(files, AttachFile.CREATOR);
	}
	
	public void setFiles(AttachFile[] files) {
		this.files = files;
	}
	
	public AttachFile[] getFiles() {
		return files;
	}	
}