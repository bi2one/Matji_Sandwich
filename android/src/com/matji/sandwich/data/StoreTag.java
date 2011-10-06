package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreTag extends Tag {

    private int store_id;
	private Store store;

    public StoreTag() {
        super();
    }
		
    public StoreTag(Parcel in) {
        super(in);
    }
    
	public static final Parcelable.Creator<StoreTag> CREATOR = new Parcelable.Creator<StoreTag>() {
		public StoreTag createFromParcel(Parcel in) {
			return new StoreTag(in);
		}

		public StoreTag[] newArray(int size) {
			return new StoreTag[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
	    super.writeToParcel(dest, arg1);
		dest.writeInt(store_id);
		dest.writeValue(store);
	}

	@Override
	public void readFromParcel(Parcel in) {
	    super.readFromParcel(in);
		store_id = in.readInt();
		store = Store.class.cast(in.readValue(Store.class.getClassLoader()));
	}
	
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	
	public int getStoreId() {
		return store_id;
	}
	
	public void setStore(Store store) {
		this.store = store;
	}
	
	public Store getStore() {
		return store;
	}
}