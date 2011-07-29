package com.matji.sandwich.util.adapter;

import com.google.android.maps.GeoPoint;
import android.location.Location;

public class LocationToGeoPointAdapter extends GeoPoint {
    public LocationToGeoPointAdapter(Location location) {
	super((int)(location.getLatitude() * 1E6),
	      (int)(location.getLongitude() * 1E6));
    }
}