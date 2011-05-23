package com.matji.sandwich.overlay;

import com.google.android.maps.OverlayItem;
import com.google.android.maps.GeoPoint;

public class PinOverlayItem extends OverlayItem {
    private String count;
    
    public PinOverlayItem(GeoPoint point, String title, String snippet) {
	super(point, title, snippet);
    }
	
    public PinOverlayItem(GeoPoint point, String title, String snippet, String count) {
	super(point, title, snippet);
	this.count = count;
    }
	
    protected String getCount() {
	return count;
    }
}
