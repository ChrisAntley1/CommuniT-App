package com.example.emergencyapp;

public class UserAddress {

    public String streetAddress;
    public String city;
    public String state;
    public Integer zipCode;

    public  UserAddress(){
        this.streetAddress = "defaultAddress";
        this.city = "defaultCity";
        this.state = "defaultState";
        this.zipCode = 11111;

    }

    public UserAddress(String streetAddress, String city, String state, Integer zipCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

}
