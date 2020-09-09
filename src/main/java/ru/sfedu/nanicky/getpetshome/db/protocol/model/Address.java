package ru.sfedu.nanicky.getpetshome.db.protocol.model;

public class Address extends IdEntity {
    private String city;
    private String street;
    private String houseNumber;

    public Address(long id, String city, String street, String houseNumber) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
