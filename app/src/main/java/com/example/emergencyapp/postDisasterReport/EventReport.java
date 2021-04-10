package com.example.emergencyapp.postDisasterReport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


class EventReport implements Serializable {


    public String captainName;
    public String captainID;
    public String blockName;
    public String blockID;
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

    public String toString(){

        String result = "";

        result+= "Block: " + blockName +'\n';
        result+= "Captain: " + captainName + '\n';

        if(gas_detected){
            result += "Smell of gas detected.\n";
        }
        if(fire_or_smoke_detected){
            result += "Fire or smoke detected.\n";
        }
        if(flooding_observed){
            result += "Flooding observed.\n";
        }
        if(structural_damaged_observed){
            result += "One or more homes significantly damaged.\n";
        }

        ArrayList<String> members = new ArrayList(injuredMembers.values());

        result += "Households with injuries:\n";

        if(!members.isEmpty()){
            for(String name: members){
                result += "  -" + name + "\n";
            }

        }

        else result += "none\n";
        return result;
    }
}
