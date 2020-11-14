package com.mcubes.minoxidilbd.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 10/19/2020.
 */
@Entity
public class ShippingInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipping_address_sequence")
    @SequenceGenerator(name = "shipping_address_sequence", sequenceName = "shipping_address_sequence", allocationSize = 1)
    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private String apartment;
    private String city;
    private String country;
    private String postalCode;

    public ShippingInformation(){}

    public ShippingInformation(String firstName, String lastName, String address, String apartment, String city,
                               String country, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.apartment = apartment;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "ShippingInformation{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", apartment='" + apartment + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
