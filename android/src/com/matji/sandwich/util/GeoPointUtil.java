package com.matji.sandwich.util;

import com.google.android.maps.GeoPoint;

public class GeoPointUtil {
    private static final int EQUAL_BOUND_LAT = 100;
    private static final int EQUAL_BOUND_LNG = 100;
    
    public static boolean geoPointEquals(GeoPoint p1, GeoPoint p2) {
	if (p1 == null || p2 == null) {
	    return false;
	}
	    
	int p1Lat = p1.getLatitudeE6();
	int p1Lng = p1.getLongitudeE6();
	int p2Lat = p2.getLatitudeE6();
	int p2Lng = p2.getLongitudeE6();
	return (p1Lat <= p2Lat + EQUAL_BOUND_LAT && p1Lat >= p2Lat - EQUAL_BOUND_LAT &&
		p1Lng <= p2Lng + EQUAL_BOUND_LNG && p1Lng >= p2Lng - EQUAL_BOUND_LNG);
    }
}