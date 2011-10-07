package com.matji.sandwich.map;

import com.google.android.maps.GeoPoint;

import java.lang.ref.WeakReference;

import com.matji.sandwich.util.GeoPointUtil;

public class MapAsyncTask extends Thread {
    private static final int MAP_CENTER_UPDATE_TICK = 200;
    private static final int MAP_CENTER_UPDATE_ANIMATION_TICK = 400;
    private static volatile MapAsyncTask mapAsyncTask;
    private MatjiMapView mapView;
    private boolean stopFlag = false;
    private GeoPoint mapCenter;
    private GeoPoint newMapCenter;
    private WeakReference<MatjiMapCenterListener> listenerRef;

    private MapAsyncTask() { }

    public void setMapView(MatjiMapView mapView) {
	this.mapView = mapView;
    }
    
    public static MapAsyncTask getInstance(MatjiMapView mapView) {
	if (mapAsyncTask == null) {
	    synchronized(MapAsyncTask.class) {
		if (mapAsyncTask == null) {
		    mapAsyncTask = new MapAsyncTask();
		}
	    }
	}
	mapAsyncTask.setMapView(mapView);

	return mapAsyncTask;
    }

    public synchronized void startMapCenterThread(GeoPoint startPoint) {
	if (!isAlive()) {
	    setMapCenter(startPoint);
	    if (listenerRef != null) {
		listenerRef.get().onMapCenterChanged(mapCenter);
	    }
	    start();
	}
    }

    public synchronized void stopMapCenterThread() {
	if (isAlive())
	    interrupt();
    }
	
    private void threadSleep(int time) {
	try {
	    Thread.sleep(time);
	} catch(InterruptedException e) {
	    e.printStackTrace();
	}
    }

    public void setMapCenter(GeoPoint point) {
	this.mapCenter = point;
    }

    public void setMapCenterListener(MatjiMapCenterListener listener) {
	this.listenerRef = new WeakReference(listener);
    }
	
    public void run() {
	while(!stopFlag) {
	    if (listenerRef != null) {
		threadSleep(MAP_CENTER_UPDATE_TICK);
		newMapCenter = mapView.getMapCenter();

		if (GeoPointUtil.geoPointEquals(mapCenter, newMapCenter))
		    continue;
		    
		while (!GeoPointUtil.geoPointEquals(mapCenter, newMapCenter)) {
		    mapCenter = newMapCenter;
		    threadSleep(MAP_CENTER_UPDATE_ANIMATION_TICK);
		    newMapCenter = mapView.getMapCenter();
		}
		mapCenter = newMapCenter;
		listenerRef.get().onMapCenterChanged(mapCenter);
	    }
	}
    }
}

