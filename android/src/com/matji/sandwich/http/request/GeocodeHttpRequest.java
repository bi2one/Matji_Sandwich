package com.matji.sandwich.http.request;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;
import java.util.Locale;

import com.google.android.maps.GeoPoint;

import com.matji.sandwich.data.MatjiData;
// import com.matji.sandwich.data.GeocodeMatjiData;
import com.matji.sandwich.http.parser.GeocodeParser;
import com.matji.sandwich.exception.GeocodeLocationInvalidMatjiException;
import com.matji.sandwich.exception.GeocodeSearchInvalidMatjiException;
import com.matji.sandwich.exception.MatjiException;

public class GeocodeHttpRequest extends HttpRequest {
    private static final String REQUEST_URL = "http://maps.googleapis.com/maps/api/geocode/json";
    private String sensor = "true";

    public GeocodeHttpRequest(Context context) {
	super(context);
	serverDomain = REQUEST_URL;
    }

    /**
     * address string을 통해서 geocoding action을 설정한다.
     *
     * @param address 위치를 지정할 address
     * @param country geocoding결과 언어의 국가
     */
    public void actionGeocoding(String address, Locale locale) {
	httpMethod = HttpMethod.HTTP_GET;

	getHashtable.clear();
	getHashtable.put("address", address);
	getHashtable.put("sensor", sensor);
	getHashtable.put("language", locale.getLanguage());
	parser = new GeocodeParser(context);
    }

    /**
     * Location객체를 통해서 지정하는 역 geocoding action을 설정한다.
     *
     * @param location 위치를 지정할 Location
     * @param country geocoding결과 언어의 국가
     */
    public void actionReverseGeocodingByLocation(Location location, Locale locale) {
	actionReverseGeocodingByGeoPoint(new GeoPoint((int)(location.getLatitude() * 1E6),
						      (int)(location.getLongitude() * 1E6)),
					 locale);
    }

    /**
     * GeoPoint객체를 통해서 지정하는 역 geocoding action을 설정한다.
     *
     * @param point 위치를 지정할 GeoPoint
     * @param country geocoding결과 언어의 국가
     */
    public void actionReverseGeocodingByGeoPoint(GeoPoint point, Locale locale) {
	Double lat = (double)point.getLatitudeE6() / 1E6;
	Double lng = (double)point.getLongitudeE6() / 1E6;
	httpMethod = HttpMethod.HTTP_GET;
	
	getHashtable.clear();
	getHashtable.put("latlng", lat + "," + lng);
	getHashtable.put("sensor", sensor);
	getHashtable.put("language", locale.getLanguage());
	parser = new GeocodeParser(context);
    }
}
