package com.matji.sandwich.overlay;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.content.Context;
import android.util.Log;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

import com.matji.sandwich.R;

public class StoreItemizedOverlay extends ItemizedOverlay {
    private Context mContext;
    private MapView mMapView;
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

    public StoreItemizedOverlay(Context context, MapView view) {
	super(boundCenterBottom(context.getResources().getDrawable(R.drawable.marker_01)));
	mMapView = view;
	mContext = context;
    }

    protected OverlayItem createItem(int i) {
	return mOverlays.get(i);
    }

    public void addOverlay(GeoPoint geoPoint) {
	if (size() == 0) {
	    mMapView.getOverlays().add(this);
	}

	OverlayItem overlayitem = new OverlayItem(geoPoint, null, null);
	mOverlays.add(overlayitem);
	populate();
    }

    public int size() {
	return mOverlays.size();
    }

    // public void draw(Canvas canvas, MapView mapview, boolean arg2) {
	
    // }

    // public static class StoreOverlayItem extends OverlayItem {
    // 	public StoreOverlayItem(GeoPoint point, String title, String snippet) {
    // 	}
    // }
}

