package com.matji.sandwich.data;

import android.location.Address;

public class AddressMatjiData extends MatjiData {
    private Address address;
	
    public AddressMatjiData(Address address) {
	this.address = address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public Address getAddress() {
	return address;
    }
}