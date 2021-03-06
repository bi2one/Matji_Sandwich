package com.matji.sandwich.map;

import com.google.android.maps.MapView;
import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

/**
 * 맵의 중심 좌표를 {@link MatjiMapCenterListener}를 통해 callback받을
 * 수 있는 기능을 제공하는 MapView.
 *
 * @author bizone
 * 
 */
public class MatjiMapView extends MapView implements MatjiMapViewInterface {
	private WeakReference<MatjiMapCenterListener> listenerRef;

	public MatjiMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 맵 중심을 체크하는 Thread를 동작시킨다.
	 */
	public synchronized void startMapCenterThread() {
		startMapCenterThread(getMapCenter());
	}

	public synchronized void startMapCenterThreadNotFirstLoading() {
		MapAsyncTask.getInstance(this, listenerRef.get()).startMapCenterThreadNotFirstLoading();
	}

	/**
	 * 맵 중심을 체크하는 Thread를 동작시킨다.
	 * 
	 * @param startPoint 맵 중심잡기 Thread를 수행할 때, 기본 위치
	 * 
	 */
	public synchronized void startMapCenterThread(GeoPoint startPoint) {
		MapAsyncTask.getInstance(this, listenerRef.get()).startMapCenterThread(startPoint);
	}

	public synchronized void stopMapCenterThread() {
		MapAsyncTask.getInstance(this, listenerRef.get()).stopMapCenterThread();
	}

	/**
	 * 맵 중심좌표를 callback으로 받는 {@link MatjiMapCenterListener}를 등록한다.
	 *
	 * @param listener 등록할 listener
	 */
	public void setMapCenterListener(MatjiMapCenterListener listener) {
		this.listenerRef = new WeakReference(listener);
		MapAsyncTask.getInstance(this, listenerRef.get()).setMapCenterListener(listener);
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