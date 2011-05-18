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
	
}

	
	