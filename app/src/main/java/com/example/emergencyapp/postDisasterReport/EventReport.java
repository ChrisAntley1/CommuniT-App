package com.example.emergencyapp.postDisasterReport;

import java.io.Serializable;
import java.util.HashMap;


class EventReport implements Serializable {


    public String captainName;
    public String userID;
    public String blockAddress; //probably something other than a string
    public Boolean gas_detected;
    public Boolean fire_or_smoke_detected;
    public Boolean flooding_observed;
    public Boolean structural_damaged_observed;
    public HashMap injuredMembers;

    //medical report information

    public EventReport(){

    }

    public EventReport(Boolean gas, Boolean fire, Boolean flood, Boolean structural){
        gas_detected = gas;
        fire_or_smoke_detected = fire;
        flooding_observed = flood;
        structural_damaged_observed = structural;
        injuredMembers = new HashMap<String, String>();
    }
}
