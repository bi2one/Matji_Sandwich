package com.matji.sandwich.data;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchResult extends MatjiData {
    private int totalCount;
    private ArrayList<MatjiData> data;

    public SearchResult() {}
    public SearchResult(Parcel in) {
        readFromParcel(in);
    }
    
    public static final Parcelable.Creator<SearchResult> CREATOR = new Parcelable.Creator<SearchResult>() {
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }
        
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public void writeToParcel(Parcel dest, int arg1) {
        dest.writeInt(totalCount);
        dest.writeTypedList(data);
    }
    
    public void readFromParcel(Parcel in) {
        totalCount = in.readInt();
        data = new ArrayList<MatjiData>();
        in.readTypedList(data, MatjiData.CREATOR);
    }
    
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
    public int getTotalCount() {
        return totalCount;
    }

    public void setData(ArrayList<MatjiData> data) {
        this.data = data;
    }

    public ArrayList<MatjiData> getData() {
        return data;
    }
}