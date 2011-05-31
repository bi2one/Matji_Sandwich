package com.matji.sandwich.overlay;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.content.Context;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import com.matji.sandwich.R;

public class StoreItemizedOverlay extends ItemizedOverlay {
    private Context mContext;
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

    public StoreItemizedOverlay(Context context) {
	super(boundCenterBottom(context.getResources().getDrawable(R.drawable.marker_01)));

	mContext = context;
    }

    protected OverlayItem createItem(int i) {
	// TODO Auto-generated method stub
	return mOverlays.get(i); 
    }

    public int size() {
	// TODO Auto-generated method stub
	return  mOverlays.size();
    }
 
    public void addOverlay(OverlayItem overlay) { 
	mOverlays.add(overlay);
	populate();
    }

    // public void draw(Canvas canvas, MapView mapview, boolean arg2) {
	
    // }

    // public static class StoreOverlayItem extends OverlayItem {
    // 	public StoreOverlayItem(GeoPoint point, String title, String snippet) {
    // 	}
    // }
}

