package com.matji.sandwich.dataprovider;

import android.content.SharedPreferences;
import android.content.Context;

public class MatjiStorageDataProvider {
    private volatile MatjiStorageDataProvider provider;
    private Context context;

    private MatjiStorageDataProvider() { }

    public MatjiStorageDataProvider getInstance(Context context) {
	return null;
    }
}