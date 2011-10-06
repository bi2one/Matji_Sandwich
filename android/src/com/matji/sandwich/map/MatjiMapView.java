package com.matji.sandwich.map;

import com.google.android.maps.MapView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import android.os.AsyncTask;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.util.GeoPointUtil;

/**
 * 맵의 중심 좌표를 {@link MatjiMapCenterListener}를 통해 callback받을
 * 수 있는 기능을 제공하는 MapView.
 *
 * @author bizone
 * 
 */
public class MatjiMapView extends MapView implements MatjiMapViewInterface {
    private static final int MAP_CENTER_UPDATE_TICK = 200;
    private static final int MAP_CENTER_UPDATE_ANIMATION_TICK = 400;
    private MatjiMapCenterListener listener;
    private MapAsyncTask asyncTask;
    private GeoPoint mapCenter;
    private GeoPoint newMapCenter;
    // private GeoPoint tempMapCenter;
    private SessionMapUtil sessionUtil;
    private MapController mapController;
	
    public MatjiMapView(Context context, AttributeSet attrs) {
	super(context, attrs);
	sessionUtil = new SessionMapUtil(context);
	mapController = getController();
    }

    /**
     * 맵 중심을 체크하는 Thread를 동작시킨다.
     */
    public synchronized void startMapCenterThread() {
	startMapCenterThread(getMapCenter());
    }

    /**
     * 맵 중심을 체크하는 Thread를 동작시킨다.
     * 
     * @param startPoint 맵 중심잡기 Thread를 수행할 때, 기본 위치
     * 
     */
    public synchronized void startMapCenterThread(GeoPoint startPoint) {
	mapCenter = startPoint;
	if (listener != null) {
	    listener.onMapCenterChanged(mapCenter);
	}

	if (asyncTask == null) {
	    asyncTask = new MapAsyncTask();
	    asyncTask.start();
	}
    }

    public synchronized void stopMapCenterThread() {
	if (asyncTask != null) {
	    asyncTask.interrupt();
	    asyncTask = null;
	}
    }

    /**
     * 맵 중심좌표를 callback으로 받는 {@link MatjiMapCenterListener}를 등록한다.
     *
     * @param listener 등록할 listener
     */
    public void setMapCenterListener(MatjiMapCenterListener listener) {
	this.listener = listener;
    }

    private class MapAsyncTask extends Thread {
	private boolean stopFlag = false;

	private void threadSleep(int time) {
	    try {
		Thread.sleep(time);
	    } catch(InterruptedException e) {
		e.printStackTrace();
	    }
	}
	
	public void run() {
	    while(!stopFlag) {
	    	if (listener != null) {
	    	    threadSleep(MAP_CENTER_UPDATE_TICK);
	    	    newMapCenter = getMapCenter();

	    	    if (GeoPointUtil.geoPointEquals(mapCenter, newMapCenter))
	    	    	continue;
		    
	    	    while (!GeoPointUtil.geoPointEquals(mapCenter, newMapCenter)) {
	    	    	mapCenter = newMapCenter;
	    	    	threadSleep(MAP_CENTER_UPDATE_ANIMATION_TICK);
	    	    	newMapCenter = getMapCenter();
	    	    }
	    	    mapCenter = newMapCenter;
	    	    listener.onMapCenterChanged(mapCenter);
	    	}
	    }
	}
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
}