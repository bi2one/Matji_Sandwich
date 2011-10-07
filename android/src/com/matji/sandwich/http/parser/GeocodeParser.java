package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.data.AddressComponent;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.exception.GeocodeLocationInvalidMatjiException;
import com.matji.sandwich.exception.GeocodeZeroResultMatjiException;
import com.matji.sandwich.exception.GeocodeSearchInvalidMatjiException;

import org.json.JSONException;
import org.json.JSONObject;

public class GeocodeParser implements MatjiParser {
    private JsonParser parser;
    
    public GeocodeParser() {
	this.parser = new JsonParser();
    }

    public ArrayList<MatjiData> parseToMatjiDataList(String data) throws MatjiException {
	String validData = getValidData(data);
	JsonElement element = null;
	ArrayList<MatjiData> result = new ArrayList<MatjiData>();
	
	try {
	    element = parser.parse(validData);
	    JsonArray addresses = element.getAsJsonArray();
	    for (JsonElement address : addresses) {
		result.add(parseToGeocodeAddress(address));
	    }
	} catch (JsonParseException e) {
	    e.printStackTrace();
	    throw new JSONMatjiException();
	}

	return result;
    }

    private JsonObject getJsonObjectOrNull(JsonObject object, String index) {
	if (object == null)
	    return null;
	else
	    return object.getAsJsonObject(index);
    }

    private JsonArray getJsonArrayOrNull(JsonObject object, String index) {
	if (object == null)
	    return null;
	else
	    return object.getAsJsonArray(index);
    }
    
    private double getJsonDoubleOrNull(JsonObject object, String index) {
	if (object == null)
	    return 0;
	else
	    return object.getAsJsonPrimitive(index).getAsDouble();
    }

    private String getJsonStringOrNull(JsonObject object, String index) {
	if (object == null)
	    return null;
	else
	    return object.getAsJsonPrimitive(index).getAsString();
    }
    
    private GeocodeAddress parseToGeocodeAddress(JsonElement address) throws JsonParseException {
	GeocodeAddress result = new GeocodeAddress();
	JsonObject addressObject = address.getAsJsonObject();
	JsonObject addressGeometryObject = getJsonObjectOrNull(addressObject, "geometry");
	JsonObject addressGeometryBoundObject = getJsonObjectOrNull(addressGeometryObject, "bounds");
	JsonObject addressGeometryBoundNEObject = getJsonObjectOrNull(addressGeometryBoundObject, "northeast");
	JsonObject addressGeometryBoundSWObject = getJsonObjectOrNull(addressGeometryBoundObject, "southwest");
	JsonObject addressGeometryLocationObject = getJsonObjectOrNull(addressGeometryObject, "location");
	JsonObject addressGeometryViewportObject = getJsonObjectOrNull(addressGeometryObject, "viewport");
	JsonObject addressGeometryViewportNEObject = getJsonObjectOrNull(addressGeometryViewportObject, "northeast");
	JsonObject addressGeometryViewportSWObject = getJsonObjectOrNull(addressGeometryViewportObject, "southwest");

	// set address components
	JsonArray addressComponentArray = getJsonArrayOrNull(addressObject, "address_components");
	ArrayList<AddressComponent> addressComponents = new ArrayList<AddressComponent>();
	
	for (JsonElement addressComponentElement : addressComponentArray) {
	    addressComponents.add(parseToAddressComponent(addressComponentElement));
	}

	// set types
	JsonArray typeArray = getJsonArrayOrNull(addressObject, "types");
	ArrayList<String> types = new ArrayList<String>();
	
	for (JsonElement typeElement : typeArray) {
	    types.add(typeElement.getAsString());
	}
	
	result.setAddressComponents(addressComponents);
	result.setFormattedAddress(getJsonStringOrNull(addressObject, "formatted_address"));
	result.setBoundNELat(getJsonDoubleOrNull(addressGeometryBoundNEObject, "lat"));
	result.setBoundNELng(getJsonDoubleOrNull(addressGeometryBoundNEObject, "lng"));
	result.setBoundSWLat(getJsonDoubleOrNull(addressGeometryBoundSWObject, "lat"));
	result.setBoundSWLng(getJsonDoubleOrNull(addressGeometryBoundSWObject, "lng"));
	result.setLocationLat(getJsonDoubleOrNull(addressGeometryLocationObject, "lat"));
	result.setLocationLng(getJsonDoubleOrNull(addressGeometryLocationObject, "lng"));
	result.setLocationType(getJsonStringOrNull(addressGeometryObject, "location_type"));
	result.setViewportNELat(getJsonDoubleOrNull(addressGeometryViewportNEObject, "lat"));
	result.setViewportNELng(getJsonDoubleOrNull(addressGeometryViewportNEObject, "lng"));
	result.setViewportSWLat(getJsonDoubleOrNull(addressGeometryViewportSWObject, "lat"));
	result.setViewportSWLng(getJsonDoubleOrNull(addressGeometryViewportSWObject, "lng"));
	result.setTypes(types);
	
	return result;
    }

    private AddressComponent parseToAddressComponent(JsonElement component) throws JsonParseException {
	JsonObject componentObject = component.getAsJsonObject();
	AddressComponent result = new AddressComponent();
	JsonArray typeArray = getJsonArrayOrNull(componentObject, "types");
	ArrayList<String> types = new ArrayList<String>();
	
	for (JsonElement typeElement : typeArray) {
	    types.add(typeElement.getAsString());
	}

	result.setLongName(getJsonStringOrNull(componentObject, "long_name"));
	result.setShortName(getJsonStringOrNull(componentObject, "short_name"));
	result.setTypes(types);
	return result;
    }

    private String getValidData(String data) throws MatjiException {
	try {
	    JSONObject json = new JSONObject(data);
	    assertStatusOk(json.getString("status"));
	    String resultData = json.getString("results");
	    assertDataIsNotNull(resultData);
	    return resultData;
	} catch (JSONException e) {
	    throw new JSONMatjiException();
	}
    }

    private void assertStatusOk(String status) throws MatjiException {
	if (status.equals("OK")) {
	} else if (status.equals("ZERO_RESULTS")) {
	    throw new GeocodeZeroResultMatjiException();
	} else if (status.equals("OVER_QUERY_LIMIT")) {
	    throw new GeocodeLocationInvalidMatjiException();
	} else if (status.equals("REQUEST_DENIED")) {
	    throw new GeocodeLocationInvalidMatjiException();
	} else if (status.equals("INVALID_REQUEST")) {
	    throw new GeocodeLocationInvalidMatjiException();
	} else {
	    throw new GeocodeLocationInvalidMatjiException();
	}
	return ;
    }

    private void assertDataIsNotNull(String data) throws MatjiException {
	if (data == null || data.equals("null") || data.equals("NULL") || data.equals("Null")) {
	    throw new GeocodeSearchInvalidMatjiException();
	} else {
	}
	return ;
    }
}