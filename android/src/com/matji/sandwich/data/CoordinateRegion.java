package com.matji.sandwich.data;

import com.google.android.maps.GeoPoint;


public class CoordinateRegion {
	private GeoPoint center;
	private int latitudeSpan;
	private int longitudeSpan;
	
	public CoordinateRegion(GeoPoint center, int latitudeSpan, int longitudeSpan) {
		this.center = center;
		this.latitudeSpan = latitudeSpan;
		this.longitudeSpan = longitudeSpan;
	}
		
	public int getLatitudeSpan(){
		return latitudeSpan;
	}
	
	public int getLongitudeSpan(){
		return longitudeSpan;
	}
	
	public GeoPoint getCenter(){
		return center;
	}
	
	public GeoPoint getSWGeoPoint(){
		return new GeoPoint(center.getLatitudeE6() - latitudeSpan / 2, center.getLongitudeE6() - longitudeSpan / 2); 
	}
	
	public GeoPoint getNEGeoPoint(){
		return new GeoPoint(center.getLatitudeE6() + latitudeSpan / 2, center.getLongitudeE6() + longitudeSpan / 2);				
	}
	
}

	
	