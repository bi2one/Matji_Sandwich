package com.matji.sandwich.session;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.data.provider.PreferenceProvider;

import com.google.android.maps.GeoPoint;

import java.io.File;
import java.io.NotSerializableException;
import java.util.ArrayList;

public class SessionGeocodeUtil {
    private Session session;
    private PreferenceProvider preferenceProvider;
    
    public SessionGeocodeUtil(Context context) {
	session = Session.getInstance(context);
	preferenceProvider = session.getPreferenceProvider();
    }

    // public void setCountryCode(String countryCode) {
    // 	preferenceProvider.setString(SessionIndex.WRITE_POST_TAGS, tags.trim());
    // }

    // public String getCountryCode() {
    // 	return preferenceProvider.getString(SessionIndex.WRITE_POST_TAGS, BASIC_TAGS);
    // }
}