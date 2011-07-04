package com.matji.sandwich.http.request;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.Address;

import java.util.ArrayList;
import java.util.Locale;
import java.util.List;
import java.io.IOException;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.AddressMatjiData;
import com.matji.sandwich.exception.GeocodeLocationInvalidMatjiException;
import com.matji.sandwich.exception.MatjiException;

public class GeocodeHttpRequest extends HttpRequest {
    private static final Locale GEOCODE_LOCALE = Locale.KOREA;
    private GeoPoint _point;
    private int _listCount;
    private Geocoder _geocoder;
    
    public GeocodeHttpRequest(Context context) {
	super(context);
	_geocoder = new Geocoder(context, GEOCODE_LOCALE);
    }

    public void actionFromLocation(Location loc, int listCount) {
	_point = new GeoPoint((int)(loc.getLatitude()*1E6), (int)(loc.getLongitude()*1E6));
	_listCount = listCount;
    }

    public void actionFromGeoPoint(GeoPoint point, int listCount) {
	_point = point;
	_listCount = listCount;
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	List<Address> geocoderResult = null;

	try {
	    geocoderResult = _geocoder.getFromLocation((float)_point.getLatitudeE6() / 1E6,
						       (float)_point.getLongitudeE6() / 1E6,
						       _listCount);
	} catch(IOException e) {
	    e.printStackTrace();
	    throw new GeocodeLocationInvalidMatjiException();
	}
	
	ArrayList<MatjiData> result = new ArrayList<MatjiData>();

	if (geocoderResult.size() == 0) {
	    throw new GeocodeLocationInvalidMatjiException();
	}

	for (Address addr : geocoderResult) {
	    result.add(new AddressMatjiData(addr));
	}
	
	return result;
    }
}