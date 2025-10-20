package com.ae2dms.zooproject.zoo;

import com.ae2dms.zooproject.food.Compound;

import java.util.ArrayList;

public class Zoo {
    private String location;
    private ArrayList<Compound> compounds;

    public Zoo(String location, int numCompounds) {
        this.setLocation(location);
        this.compounds = new ArrayList<Compound>();
        createCompound(numCompounds);
    }

    public Zoo() {
        this("Unknown", 1);
    }

    public void createCompound(int numCompounds) {
        if (numCompounds < 1) numCompounds = 1;
        for (int i = 0; i < numCompounds; i++) {
            this.compounds.add(new Compound());
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void printInfo() {
        System.out.println("The zoo in " + location + " has " + compounds.size() + " compounds.");
    }
}


//    private int avgVisitors;
//    private static int count = 0;
//
//    public Zoo(String location) {
//        this(location, 1);
//    }
//
//	@Override
//	public String toString() {
//		return "The zoo is in " + location + " has " + compounds.size() + " compounds.";
//	}
//
//	public void setAvgVisitors(int avgVisitors) {
//    	this.avgVisitors = avgVisitors;
//	}
//
//	public static int getNumZoosCreated() {
//		return count;
//	}
//
//	public void changeZoo(Zoo zoo, int avgVisitors) {
//		zoo.setAvgVisitors(avgVisitors);
//	}

//}
