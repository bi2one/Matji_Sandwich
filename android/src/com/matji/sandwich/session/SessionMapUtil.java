package com.matji.sandwich.session;

import android.content.Context;

import com.google.android.maps.GeoPoint;

import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.http.request.GeocodeHttpRequest.Country;

public class SessionMapUtil {
    private static final int LAT_NEAR_BASIC_SPAN = 3000;
    private static final int LNG_NEAR_BASIC_SPAN = 3000;
    private static final int BASIC_LATITUDE_SW = 37000000;
    private static final int BASIC_LATITUDE_NE = 126600000;
    private static final int BASIC_LONGITUDE_SW = 37400000;
    private static final int BASIC_LONGITUDE_NE = 126900000;
    private static final int BASIC_CENTER_LAT = 37200000;
    private static final int BASIC_CENTER_LNG = 126750000;
    
    private Session session;
    private PreferenceProvider preferenceProvider;
    
    public SessionMapUtil(Context context) {
	session = Session.getInstance(context);
	preferenceProvider = session.getPreferenceProvider();
    }

    public void setBound(GeoPoint neBound, GeoPoint swBound) {
	preferenceProvider.setInt(SessionIndex.MAP_BOUND_LATITUDE_NE, neBound.getLatitudeE6());
	preferenceProvider.setInt(SessionIndex.MAP_BOUND_LATITUDE_SW, swBound.getLatitudeE6());
	preferenceProvider.setInt(SessionIndex.MAP_BOUND_LONGITUDE_NE, neBound.getLongitudeE6());
	preferenceProvider.setInt(SessionIndex.MAP_BOUND_LONGITUDE_SW, swBound.getLongitudeE6());
    }

    public void setCenter(GeoPoint centerPoint) {
	preferenceProvider.setInt(SessionIndex.MAP_BOUND_CENTER_LATITUDE, centerPoint.getLatitudeE6());
	preferenceProvider.setInt(SessionIndex.MAP_BOUND_CENTER_LONGITUDE, centerPoint.getLongitudeE6());
    }

    public void setCenterAddress(String address) {
	preferenceProvider.setString(SessionIndex.MAP_ADDRESS, address);
    }

    public String getCenterAddress() {
	return preferenceProvider.getString(SessionIndex.MAP_ADDRESS, "");
    }

    public GeoPoint getCenter() {
	int centerLat = preferenceProvider.getInt(SessionIndex.MAP_BOUND_CENTER_LATITUDE, BASIC_CENTER_LAT);
	int centerLng = preferenceProvider.getInt(SessionIndex.MAP_BOUND_CENTER_LONGITUDE, BASIC_CENTER_LNG);
	return new GeoPoint(centerLat, centerLng);
    }

    public void setNearBound(GeoPoint centerPoint) {
	int centerLat = centerPoint.getLatitudeE6();
	int centerLng = centerPoint.getLongitudeE6();
	GeoPoint neBound = new GeoPoint(centerLat + LAT_NEAR_BASIC_SPAN,
					centerLng + LNG_NEAR_BASIC_SPAN);
	GeoPoint swBound = new GeoPoint(centerLat - LAT_NEAR_BASIC_SPAN,
					centerLng - LNG_NEAR_BASIC_SPAN);
	setBound(neBound, swBound);
    }

    public GeoPoint getNEBound() {
	int latNE = preferenceProvider.getInt(SessionIndex.MAP_BOUND_LATITUDE_NE, BASIC_LATITUDE_NE);
	int lngNE = preferenceProvider.getInt(SessionIndex.MAP_BOUND_LONGITUDE_NE, BASIC_LONGITUDE_NE);
	return new GeoPoint(latNE, lngNE);
    }

    public GeoPoint getSWBound() {
	int latSW = preferenceProvider.getInt(SessionIndex.MAP_BOUND_LATITUDE_SW, BASIC_LATITUDE_SW);
	int lngSW = preferenceProvider.getInt(SessionIndex.MAP_BOUND_LONGITUDE_SW, BASIC_LONGITUDE_SW);
	return new GeoPoint(latSW, lngSW);
    }

    public Country getCurrentCountry() {
	return Country.KOREA;
    }
}