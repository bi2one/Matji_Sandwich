package com.matji.sandwich.overlay;

import android.content.Context;

import com.google.android.maps.GeoPoint;

import com.matji.sandwich.R;

public class StoreLocationOverlay extends BottomCenterOverlay {
    public StoreLocationOverlay(Context context, GeoPoint point) {
	super(context, point, R.drawable.marker_03);
    }
}