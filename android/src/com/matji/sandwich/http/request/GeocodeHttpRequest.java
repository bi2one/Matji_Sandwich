package com.matji.sandwich.http.request;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;

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

    /**
     * Geocoding지원 가능한 국가들. 이곳에 추가하면 바로 적용이 가능하다.
     */
    public static enum Country {
	CHINA, JAPAN, USA, KOREA;
	public String toLanguageCode() {
	    switch(this) {
	    case CHINA:
		return "zh-CN";
	    case JAPAN:
		return "ja";
	    case USA:
		return "en";
	    case KOREA:
		return "ko";
	    default:
		return null;
	    }
	}
    }

    public GeocodeHttpRequest(Context context) {
	super(context);
	serverDomain = REQUEST_URL;
    }

    /**
     * Location객체를 통해서 지정하는 역 geocoding action을 설정한다.
     *
     * @param location 위치를 지정할 Location
     * @param country geocoding결과 언어의 국가
     */
    public void actionReverseGeocodingByLocation(Location location, Country country) {
	actionReverseGeocodingByGeoPoint(new GeoPoint((int)(location.getLatitude() * 1E6),
						      (int)(location.getLongitude() * 1E6)),
					 country);
    }

    /**
     * GeoPoint객체를 통해서 지정하는 역 geocoding action을 설정한다.
     *
     * @param point 위치를 지정할 GeoPoint
     * @param country geocoding결과 언어의 국가
     */
    public void actionReverseGeocodingByGeoPoint(GeoPoint point, Country country) {
	Double lat = (double)point.getLatitudeE6() / 1E6;
	Double lng = (double)point.getLongitudeE6() / 1E6;
	httpMethod = HttpMethod.HTTP_GET;
	
	getHashtable.clear();
	getHashtable.put("latlng", lat + "," + lng);
	getHashtable.put("sensor", sensor);
	getHashtable.put("language", country.toLanguageCode());
	parser = new GeocodeParser(context);
    }
}
