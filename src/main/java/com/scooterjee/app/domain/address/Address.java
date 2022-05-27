package com.scooterjee.app.domain.address;

import com.scooterjee.kernel.Entity;

public class Address extends Entity<Long> {

    private final String city;
    private final String street;
    private final String number;
    private final String country;
    private final String postalCode;
    private final Double latitude;
    private final Double longitude;

    public Address(Long id, String city, String street, String number, String country, String postalCode, Double latitude, Double longitude) {
        super(id);
        this.city = city;
        this.street = street;
        this.number = number;
        this.country = country;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return this.number + " " + this.street + ", " + this.postalCode + " " + this.country;
    }
}
