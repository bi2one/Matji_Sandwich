package com.matji.sandwich.map;

import com.google.android.maps.GeoPoint;

public interface MatjiMapCenterListener {
    public void onMapCenterChanged(GeoPoint point);
}