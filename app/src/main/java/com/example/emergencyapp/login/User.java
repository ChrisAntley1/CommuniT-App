package com.example.emergencyapp.login;

import java.util.ArrayList;

public class User {

    public String name, email;
    public int zipCode;
    public ArrayList<String> communityList;

    public User(){

    }

    public User(String name, int zipCode, String email){
        this.name = name;
        this.zipCode = zipCode;
        this.email = email;
        communityList = new ArrayList<>();
    }
}
