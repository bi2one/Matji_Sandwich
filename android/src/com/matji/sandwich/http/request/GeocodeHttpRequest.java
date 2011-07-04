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
import com.matji.sandwich.exception.GeocodeSearchInvalidMatjiException;
import com.matji.sandwich.exception.MatjiException;

public class GeocodeHttpRequest extends HttpRequest {
    private static final Locale GEOCODE_LOCALE = Locale.KOREA;
    private GeoPoint _point;
    private int _listCount;
    private String _locName;
    private Geocoder _geocoder;
    private Action actionFlag;
    private enum Action { LOCATION, SEARCH }
    
    public GeocodeHttpRequest(Context context) {
	super(context);
	_geocoder = new Geocoder(context, GEOCODE_LOCALE);
    }

    public void actionFromLocation(Location loc, int listCount) {
	_point = new GeoPoint((int)(loc.getLatitude()*1E6), (int)(loc.getLongitude()*1E6));
	_listCount = listCount;
	actionFlag = Action.LOCATION;
    }

    public void actionFromGeoPoint(GeoPoint point, int listCount) {
	_point = point;
	_listCount = listCount;
	actionFlag = Action.LOCATION;
    }

    public void actionFromLocationName(String locName, int listCount) {
	_locName = locName;
	_listCount = listCount;
	actionFlag = Action.SEARCH;
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	switch(actionFlag) {
	case LOCATION:
	    return requestLocation();
	case SEARCH:
	    return requestSearch();
	}
	return null;
    }

    public ArrayList<MatjiData> requestLocation() throws MatjiException {
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

    public ArrayList<MatjiData> requestSearch() throws MatjiException {
	List<Address> geocoderResult = null;
	
	try {
	    geocoderResult = _geocoder.getFromLocationName(_locName,
							   _listCount);
	} catch(IOException e) {
	    e.printStackTrace();
	    throw new GeocodeSearchInvalidMatjiException();
	}
	
	ArrayList<MatjiData> result = new ArrayList<MatjiData>();

	if (geocoderResult.size() == 0) {
	    throw new GeocodeSearchInvalidMatjiException();
	}

	for (Address addr : geocoderResult) {
	    result.add(new AddressMatjiData(addr));
	}
	return result;    	
    }
}