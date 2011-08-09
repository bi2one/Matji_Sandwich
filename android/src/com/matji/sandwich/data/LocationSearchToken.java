package com.matji.sandwich.data;

import java.io.Serializable;

public class LocationSearchToken extends MatjiData implements Serializable {
    private String seed;
    private int latitude;
    private int longitude;

    public LocationSearchToken(String seed, int latitude, int longitude) {
	setSeed(seed);
	setLatitude(latitude);
	setLongitude(longitude);
    }

    public void setSeed(String seed) { this.seed = seed.trim(); }
    public void setLatitude(int latitude) { this.latitude = latitude; }
    public void setLongitude(int longitude) { this.longitude = longitude; }

    public String getSeed() { return seed; }
    public int getLatitude() { return latitude; }
    public int getLongitude() { return longitude; }

    public boolean equals(LocationSearchToken token) {
	return (token.getSeed().equals(getSeed()) &&
		token.getLatitude() == getLatitude() &&
		token.getLongitude() == getLongitude());
    }
}