package com.example.emergencyapp.login;

import com.example.emergencyapp.communities.CurrentCommunityObject;


public class User {

    public String name, email;
    public int zipCode;
    public CurrentCommunityObject selectedCommunity;

    public User(){

    }

    public User(String name, int zipCode, String email){
        this.name = name;
        this.zipCode = zipCode;
        this.email = email;

        selectedCommunity = new CurrentCommunityObject("0", "No communities added.");
    }
}
