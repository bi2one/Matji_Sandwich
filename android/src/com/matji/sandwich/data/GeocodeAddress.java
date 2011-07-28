package com.matji.sandwich.data;

import java.util.ArrayList;

public class GeocodeAddress extends MatjiData {
    private ArrayList<AddressComponent> addressComponents;
    private String formattedAddress;
    private int boundNELat;
    private int boundNELng;
    private int boundSWLat;
    private int boundSWLng;
    private int locationLat;
    private int locationLng;
    private String locationType;
    private int viewportNELat;
    private int viewportNELng;
    private int viewportSWLat;
    private int viewportSWLng;
    private ArrayList<String> types;
    
    public void setAddressComponents(ArrayList<AddressComponent> addressComponents) {
	this.addressComponents = addressComponents;
    }

    public void setFormattedAddress(String formattedAddress) {
	this.formattedAddress = formattedAddress;
    }

    public void setBoundNELat(double boundNELat) {
	this.boundNELat = (int)(boundNELat * 1E6);
    }

    public void setBoundNELng(double boundNELng) {
	this.boundNELng = (int)(boundNELng * 1E6);
    }

    public void setBoundSWLat(double boundSWLat) {
	this.boundSWLat = (int)(boundSWLat * 1E6);
    }

    public void setBoundSWLng(double boundSWLng) {
	this.boundSWLng = (int)(boundSWLng * 1E6);
    }

    public void setLocationLat(double locationLat) {
	this.locationLat = (int)(locationLat * 1E6);
    }

    public void setLocationLng(double locationLng) {
	this.locationLng = (int)(locationLng * 1E6);
    }

    public void setLocationType(String locationType) {
	this.locationType = locationType;
    }

    public void setViewportNELat(double viewportNELat) {
	this.viewportNELat = (int)(viewportNELat * 1E6);
    }

    public void setViewportNELng(double viewportNELng) {
	this.viewportNELng = (int)(viewportNELng * 1E6);
    }

    public void setViewportSWLat(double viewportSWLat) {
	this.viewportSWLat = (int)(viewportSWLat * 1E6);
    }

    public void setViewportSWLng(double viewportSWLng) {
	this.viewportSWLng = (int)(viewportSWLng * 1E6);
    }

    public void setTypes(ArrayList<String> types) {
	this.types = types;
    }

    public ArrayList<AddressComponent> getAddressComponents() {
	return addressComponents;
    }

    public String getFormattedAddress() {
	return formattedAddress;
    }

    public int getBoundNELat() {
	return boundNELat;
    }

    public int getBoundNELng() {
	return boundNELng;
    }

    public int getBoundSWLat() {
	return boundSWLat;
    }

    public int getBoundSWLng() {
	return boundSWLng;
    }
    
    public int getLocationLat() {
	return locationLat;
    }

    public int getLocationLng() {
	return locationLng;
    }

    public String getLocationType() {
	return locationType;
    }

    public int getViewportNELat() {
	return viewportNELat;
    }

    public int getViewportNELng() {
	return viewportNELng;
    }

    public int getViewportSWLng() {
	return viewportSWLng;
    }

    public ArrayList<String> getTypes() {
	return types;
    }

    public String getShortenFormattedAddress() {
	String country = getCountryLongName().trim();
	if (country.equals(formattedAddress.trim())) {
	    return country;
	} else if (country != null) {
	    formattedAddress = formattedAddress.replace(country, "");
	    formattedAddress = formattedAddress.replace("  ", " ");
	    return formattedAddress.trim();
	} else {
	    return formattedAddress;
	}
    }

    private String getCountryLongName() {
	for (AddressComponent addressComponent : addressComponents) {
	    ArrayList<String> types = addressComponent.getTypes();
	    for (String type : types) {
		if (type.equals("country")) {
		    return addressComponent.getLongName();
		}
	    }
	}
	return null;
    }
}