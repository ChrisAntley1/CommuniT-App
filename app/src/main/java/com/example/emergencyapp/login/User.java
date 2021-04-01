package com.example.emergencyapp.login;

import com.example.emergencyapp.communities.CommunityListEntry;


public class User {

    public String name, email;
    public int zipCode;
    public CommunityListEntry selectedCommunity;

    public User(){

    }

    public User(String name, int zipCode, String email){
        this.name = name;
        this.zipCode = zipCode;
        this.email = email;

        selectedCommunity = new CommunityListEntry("0", "No communities added.");
    }
}
