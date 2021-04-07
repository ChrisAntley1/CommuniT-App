package com.example.emergencyapp.communities;


import java.util.HashMap;

public class Community {

    public String name;
    public int zipCode;
    public HashMap<String, String> memberList;
    public Captain captain;
    public String passCode;
    public Community(){

    }

    public Community(String name, int zipCode, HashMap<String, String> memberList, Captain captain, String passCode){
        this.name = name;
        this.zipCode = zipCode;
        this.memberList = memberList;
        this.captain = captain;
        this.passCode = passCode;
    }

    @Override
    public String toString() {
        return "Community{" +
                "name='" + name + '\'' +
                ", zipCode=" + zipCode +
                ", memberList=" + memberList.toString() +
                ", captain=" + captain +
                ", passCode='" + passCode + '\'' +
                '}';
    }
}
