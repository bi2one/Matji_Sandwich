package com.matji.sandwich.overlay;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView;

public class BottomCenterOverlay extends Overlay {
    private Context context;
    private GeoPoint point;
    private int drawableId;
    
    public BottomCenterOverlay(Context context, GeoPoint point, int drawableId) {
	this.context = context;
	this.point = point;
	this.drawableId = drawableId;
    }

    public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
	Point screenPoint = new Point();
	mapView.getProjection().toPixels(point, screenPoint);

	Bitmap markerImage = BitmapFactory.decodeResource(context.getResources(), drawableId);
	canvas.drawBitmap(markerImage,
			  screenPoint.x - markerImage.getWidth() / 2,
			  screenPoint.y - markerImage.getHeight(), null);
	return true;
    }

    // public boolean onTap(GeoPoint p, MapView mapView) {
    // 	return true;
    // }
}