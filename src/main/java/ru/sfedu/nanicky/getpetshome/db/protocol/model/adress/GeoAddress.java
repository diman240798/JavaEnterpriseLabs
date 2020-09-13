package ru.sfedu.nanicky.getpetshome.db.protocol.model.adress;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;

public class GeoAddress extends IdEntity {
    private double latitude;
    private double longtitude;
    private Address address;

    public GeoAddress(long id, double latitude, double longtitude, Address address) {
        this.id = id;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
