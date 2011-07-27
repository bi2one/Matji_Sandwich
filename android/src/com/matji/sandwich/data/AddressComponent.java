package com.matji.sandwich.data;

import java.util.ArrayList;

public class AddressComponent extends MatjiData {
    private String longName;
    private String shortName;
    private ArrayList<String> types;

    public void setLongName(String longName) {
	this.longName = longName;
    }

    public void setShortName(String shortName) {
	this.shortName = shortName;
    }

    public void setTypes(ArrayList<String> types) {
	this.types = types;
    }

    public String getLongName() {
	return longName;
    }

    public String getShortName() {
	return shortName;
    }

    public ArrayList<String> getTypes() {
	return types;
    }
}