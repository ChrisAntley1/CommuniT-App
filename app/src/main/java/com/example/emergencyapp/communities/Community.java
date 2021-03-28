package com.example.emergencyapp.communities;

import java.util.ArrayList;

public class Community {

    public String name, captain;
    public int zipCode;

    public ArrayList<String> members;

    public Community(){

    }

    public Community(String name, int zipCode, String captain){
        this.name = name;
        this.captain = captain;
        this.zipCode = zipCode;
        members = new ArrayList<>();
        members.add(captain);
    }
}
