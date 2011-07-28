package com.matji.sandwich.util;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.GeocodeAddress;

import com.google.android.maps.GeoPoint;
import android.util.Log;

public class GeocodeUtil {
    public static GeocodeAddress approximateAddress(ArrayList<MatjiData> addresses, GeoPoint neBound, GeoPoint swBound) {
	double currentDistance = getDistance(neBound.getLatitudeE6(), neBound.getLongitudeE6(),
					     swBound.getLatitudeE6(), swBound.getLongitudeE6());
	GeocodeAddress result = (GeocodeAddress)addresses.get(0);
	double shortestDistance = 0;
	
	for (MatjiData addressData : addresses) {
	    GeocodeAddress address = (GeocodeAddress)addressData;
	    int neLat = address.getBoundNELat();
	    int neLng = address.getBoundNELng();
	    int swLat = address.getBoundSWLat();
	    int swLng = address.getBoundSWLng();
	    
	    if (neLat == 0 && neLng == 0 &&
		swLat == 0 && swLng == 0) {
		continue;
	    }

	    double geocodeDistance = getDistance(neLat, neLng, swLat, swLng);
	    // Log.d("=====", "==========================================");
	    // Log.d("=====", "neLat      : " + neLat);
	    // Log.d("=====", "neLng      : " + neLng);
	    // Log.d("=====", "swLat      : " + swLat);
	    // Log.d("=====", "swLng      : " + swLng);
	    // Log.d("=====", "geoDistance: " + geocodeDistance);
	    double newDistance = Math.abs(geocodeDistance - currentDistance);
	    // Log.d("=====", "newDistance: " + newDistance);
	    if (shortestDistance == 0 || newDistance < shortestDistance) {
		shortestDistance = newDistance;
		result = address;
	    } else {
		continue;
	    }
	}
	return result;
    }

    public static double getDistance(int p1x, int p1y, int p2x, int p2y) {
	return Math.sqrt(Math.pow((double)(p2x - p1x), 2) + Math.pow((double)(p2y - p1y), 2));
    }
}