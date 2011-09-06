package com.matji.sandwich.map;

import android.view.View;
import android.view.MotionEvent;
import android.util.Log;

import com.google.android.maps.Overlay;
import com.google.android.maps.MapView;

public class ClickListenerOverlay extends Overlay {
    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
	this.listener = listener;
    }
    
    public boolean onTouchEvent(MotionEvent e, MapView mapView) {
	if (listener == null) {
	    return false;
	}

	switch(e.getAction()) {
	case MotionEvent.ACTION_UP:
	    listener.onMapTouchUp(mapView);
	    break;
	case MotionEvent.ACTION_DOWN:
	    listener.onMapTouchDown(mapView);
	}
	return false;
    }

    public interface OnClickListener {
	public void onMapTouchUp(View v);
	public void onMapTouchDown(View v);
    }
}
