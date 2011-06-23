package com.matji.sandwich.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView;
import com.matji.sandwich.R;

public class CenterOverlay extends Overlay {
    private static final int MARKER_REF = R.drawable.marker_01;
    private static final int MARKER_WIDTH = 25;
    private static final int MARKER_HEIGHT = 30;
    private MapView mapView;
    private Context context;
    private Drawable marker;

    public CenterOverlay(Context context, MapView mapView) {
	this.mapView = mapView;
	this.context = context;
	marker = context.getResources().getDrawable(MARKER_REF);
    }
	
    public void draw(Canvas canvas, MapView mMapView, boolean shadow) {
	super.draw(canvas, mMapView, shadow);
	    
	int w = mMapView.getWidth()/2;
	int h = mMapView.getHeight()/2;
	    
	setMarkerBounds(w, h);
	marker.draw(canvas);
    }

    public void setMarkerBounds(int startX, int startY) {
	int halfWidth = MARKER_WIDTH / 2;
	int halfHeight = MARKER_HEIGHT / 2;

	marker.setBounds(startX - halfWidth, startY - MARKER_HEIGHT, startX + halfWidth, startY);
    }

    public void drawOverlay() {
	mapView.getOverlays().add(this);
	mapView.postInvalidate();
    }
}