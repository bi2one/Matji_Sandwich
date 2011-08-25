package com.matji.sandwich.map;

import com.google.android.maps.MapView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

public interface MatjiMapViewInterface {
    public static enum BoundType {
	MAP_BOUND_NE, MAP_BOUND_SW, MAP_BOUND_SE, MAP_BOUND_NW
    }
    
    public void startMapCenterThread();
    public void stopMapCenterThread();
    public void setMapCenterListener(MatjiMapCenterListener listener);
    public GeoPoint getBound(BoundType type);
    public MapController getController();
    public int getLatitudeSpan();
    public int getLongitudeSpan();
}