package com.matji.sandwich.map;

import com.google.android.maps.MapView;
import com.google.android.maps.GeoPoint;

import android.os.AsyncTask;
import android.content.Context;
import android.util.AttributeSet;


public class MatjiMapView extends MapView {
    private static final int MAP_CENTER_UPDATE_TICK = 200;
    private static final int MAP_CENTER_UPDATE_ANIMATION_TICK = 500;
    private MatjiMapCenterListener listener;
    private MapAsyncTask asyncTask;
    private GeoPoint mapCenter;

    public MatjiMapView(Context context, AttributeSet attrs) {
	super(context, attrs);
	mapCenter = getMapCenter();
    }

    public void startMapCenterThread() {
	asyncTask = new MapAsyncTask();
	asyncTask.execute(0);
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
	    return (p1.getLatitudeE6() == p2.getLatitudeE6() && p2.getLongitudeE6() == p2.getLongitudeE6());
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
}