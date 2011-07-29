package com.matji.sandwich.util.adapter;

import com.google.android.maps.GeoPoint;
import android.location.Location;

public class GeoPointToLocationAdapter extends Location {
    public GeoPointToLocationAdapter(GeoPoint point) {
	super("");
	setLatitude((double)point.getLatitudeE6() / 1E6);
	setLongitude((double)point.getLongitudeE6() / 1E6);
    }
}