package com.matji.sandwich.map;

import com.google.android.maps.MapView;
import com.google.android.maps.GeoPoint;

import android.os.AsyncTask;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MatjiMapView extends MapView implements OnTouchListener {
    private static final int MAP_CENTER_UPDATE_TICK = 200;
    private static final int MAP_CENTER_UPDATE_ANIMATION_TICK = 500;
    private static final int MAP_CENTER_CHANGE_BOUND_LAT = 10;
    private static final int MAP_CENTER_CHANGE_BOUND_LNG = 10;
    private MatjiMapCenterListener listener;
    private MapAsyncTask asyncTask;
    private GeoPoint mapCenter;
    public static enum BoundType {
	MAP_BOUND_NE, MAP_BOUND_SW, MAP_BOUND_SE, MAP_BOUND_NW
    }

    public MatjiMapView(Context context, AttributeSet attrs) {
	super(context, attrs);
	// mapCenter = getMapCenter();
	setOnTouchListener(this);
    }

    public void startMapCenterThread() {
	if (asyncTask == null) {
	    asyncTask = new MapAsyncTask();
	    asyncTask.execute(0);
	}
    }
				 
    public void stopMapCenterThread() {
	if (asyncTask != null) {
	    if (!asyncTask.isCancelled()) {
		asyncTask.stopTask(true);
	    }
	    asyncTask = null;
	}
    }

    public void setMapCenterListener(MatjiMapCenterListener listener) {
	this.listener = listener;
    }

    private class MapAsyncTask extends AsyncTask<Integer, Integer, Integer> {
	private boolean stopFlag = false;
	
	private void threadSleep(int time) {
	    try {
		Thread.sleep(time);
	    } catch(InterruptedException e) {
		e.printStackTrace();
	    }
	}

	public boolean stopTask(boolean mayInterruptIfRunning) {
	    stopFlag = true;
	    return super.cancel(mayInterruptIfRunning);
	}

	private boolean geoPointEquals(GeoPoint p1, GeoPoint p2) {
	    if (p1 == null || p2 == null) {
		return false;
	    }
	    
	    int p1Lat = p1.getLatitudeE6();
	    int p1Lng = p1.getLongitudeE6();
	    int p2Lat = p2.getLatitudeE6();
	    int p2Lng = p2.getLongitudeE6();
	    return (p1Lat <= p2Lat + MAP_CENTER_CHANGE_BOUND_LAT && p1Lat >= p2Lat - MAP_CENTER_CHANGE_BOUND_LAT &&
		    p1Lng <= p2Lng + MAP_CENTER_CHANGE_BOUND_LNG && p1Lng >= p2Lng - MAP_CENTER_CHANGE_BOUND_LNG);
	}
	
	protected Integer doInBackground(Integer... params) {
	    GeoPoint newMapCenter;
	    while(!stopFlag) {
		if (listener != null) {
		    threadSleep(MAP_CENTER_UPDATE_TICK);
		    newMapCenter = getMapCenter();
		    
		    if (geoPointEquals(mapCenter, newMapCenter))
		    	continue;
		    
		    while (!geoPointEquals(mapCenter, newMapCenter)) {
		    	mapCenter = newMapCenter;
		    	threadSleep(MAP_CENTER_UPDATE_ANIMATION_TICK);
		    	newMapCenter = getMapCenter();
		    }
		    mapCenter = newMapCenter;
		    listener.onMapCenterChanged(mapCenter);
		}
	    }
            return 0;
	}

	protected void onCancelled() { }
	protected void onPostExecute(Integer result) { }
	protected void onPreExecute() {	}
	protected void onProgressUpdate(Integer... values) { }
    }

    public GeoPoint getBound(BoundType type) {
	GeoPoint center = getMapCenter();
	int latBoundUnit = getLatitudeSpan() / 2;
	int lngBoundUnit = getLatitudeSpan() / 2;
	int eastLatitude = center.getLatitudeE6() + latBoundUnit;
	int westLatitude = center.getLatitudeE6() - latBoundUnit;
	int southLongitude = center.getLongitudeE6() - lngBoundUnit;
	int northLongitude = center.getLongitudeE6() + lngBoundUnit;
	
	switch(type) {
	case MAP_BOUND_NE:
	    return new GeoPoint(eastLatitude, northLongitude);
	case MAP_BOUND_SW:
	    return new GeoPoint(westLatitude, southLongitude);
	case MAP_BOUND_SE:
	    return new GeoPoint(eastLatitude, southLongitude);
	case MAP_BOUND_NW:
	    return new GeoPoint(westLatitude, northLongitude);
	}
	return null;
    }

    public boolean onTouch(View v, MotionEvent e) {
	switch(e.getAction()) {
	case MotionEvent.ACTION_DOWN:
	    stopMapCenterThread();
	    break;
	case MotionEvent.ACTION_UP:
	    startMapCenterThread();
	}
	return false;
    }
}