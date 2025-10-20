package com.ae2dms.zoolab2;

import java.util.ArrayList;
import java.util.Iterator;

public class ZooCorp {
    private ArrayList<Zoo> zoos;
    private ArrayList<Employable> personnel;

    public ZooCorp(Zoo zoo){
        this.zoos = new ArrayList<Zoo>();
        personnel = new ArrayList<Employable>();
        zoos.add(zoo);
    }

    public void addZoo(Zoo zoo){
        this.zoos.add(zoo);
    }

    public void addStaff(Employable person){
        personnel.add(person);
    }

    public void printZooInfo(){
        Iterator<Zoo> iter = zoos.iterator();
        while(iter.hasNext()){
            iter.next().printInfo();
        }
    }

    public ArrayList<Employable> getAllEmployee() {
        return personnel;
    }
}
