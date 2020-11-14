package com.mcubes.minoxidilbd.model;

/**
 * Created by A.A.MAMUN on 10/21/2020.
 */
public class OrderInformation {

    private String contactNumber; // Email
    private String firstName;
    private String lastName;
    private String address;
    private String apartment;
    private String city;
    private String country;
    private String postalCode;
    private String cartItemsCode;

    public OrderInformation() {
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public String getCartItemsCode() {
        return cartItemsCode;
    }

    public void setCartItemsCode(String cartItemsCode) {
        this.cartItemsCode = cartItemsCode;
    }

    @Override
    public String toString() {
        return "OrderInformation{" +
                "contactNumber='" + contactNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", apartment='" + apartment + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", cartItemsCode='" + cartItemsCode + '\'' +
                '}';
    }
}
