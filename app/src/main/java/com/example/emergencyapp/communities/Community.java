package com.example.emergencyapp.communities;

import java.util.ArrayList;

public class Community {

    public String name;
    public int zipCode;
    public MemberListEntry captain;

    public ArrayList<String> members;

    public Community(){

    }

    public Community(String name, int zipCode, MemberListEntry captain){
        this.name = name;
        this.captain = captain;
        this.zipCode = zipCode;
        members = new ArrayList<>();
    }
}
