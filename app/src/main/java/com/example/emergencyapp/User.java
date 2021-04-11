package com.example.emergencyapp;

import com.example.emergencyapp.communities.CurrentCommunityObject;


public class User {

    public String name, email, phoneNumber;
    public CurrentCommunityObject selectedCommunity;
    public UserAddress address;
    public User(){

    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
        this.address = new UserAddress();
        this.phoneNumber = "4045551234";
        selectedCommunity = new CurrentCommunityObject("0", "No communities added.");
    }

    public User(String name, String phoneNumber, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;

        this.address = new UserAddress();
        selectedCommunity = new CurrentCommunityObject("0", "No communities added.");
    }

    public User(String name, UserAddress address){
        this.name = name;
        this.address = address;
    }

}
