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

	public static MapAsyncTask getInstance(MatjiMapView mapView, MatjiMapCenterListener listener) {
		if (mapAsyncTask == null) {
			synchronized(MapAsyncTask.class) {
				if (mapAsyncTask == null) {
					mapAsyncTask = new MapAsyncTask();
				}
			}
		}
		mapAsyncTask.init(mapView, listener);

		return mapAsyncTask;
	}

	private void init(MatjiMapView mapView, MatjiMapCenterListener listener) {
		setMapView(mapView);
		setMapCenterListener(listener);
	}

	public synchronized void startMapCenterThread(GeoPoint startPoint) {
		setMapCenter(startPoint);
		if (listenerRef != null && listenerRef.get() != null)
			listenerRef.get().onMapCenterChanged(mapCenter);
		if (!isAlive())
			start();
	}

	public synchronized void startMapCenterThreadNotFirstLoading() {
		setMapCenter(mapView.getMapCenter());
		if (!isAlive())
			start();
	}

	public synchronized void stopMapCenterThread() {
		if (isAlive())
			stopFlag = true;
	}

	private synchronized boolean isStopped() {
		return stopFlag;
	}

	private synchronized void setStopFlag(boolean stopFlag) {
		this.stopFlag = stopFlag;
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
			if (listenerRef != null && listenerRef.get() != null) {
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
		mapAsyncTask = null;
	}
}
