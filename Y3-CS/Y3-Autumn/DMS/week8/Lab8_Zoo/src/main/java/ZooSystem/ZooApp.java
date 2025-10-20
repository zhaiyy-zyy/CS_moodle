package ZooSystem;

import java.util.Iterator;

public class ZooApp {
    public static void main(String args[]){
        Zoo.numZoos = 0;
        Zoo zL = new Zoo("London", 10);
        Zoo zT = new Zoo("Tokyo", 20);
        Zoo zN = new Zoo("New York", 30);
        Zoo zP = new Zoo("Paris", 40);
        Zoo zB = new Zoo("Ningbo", 50);
        System.out.println("numZoos: "+ Zoo.numZoos);
        zL.printInfo();

        ZooCorp zooCorp = new ZooCorp(zL);
        zooCorp.addZoo(zT);
        zooCorp.addZoo(zN);
        zooCorp.addZoo(zP);
        zooCorp.addZoo(zB);
        zooCorp.printZooInfo(); //print all zoo information added.

        Employable zk1 = new ZooKeeper("John"); //Can also use Employee
        Employable am1 = new Admin("Jane");
        zooCorp.addStaff(zk1);
        zooCorp.addStaff(am1);

        Iterator<Employable> iter = zooCorp.getAllEmployee().iterator();
        while(iter.hasNext()){
            Employable e = iter.next();
            e.setSalary(100);
            System.out.println("Christmas bonus for "
                    + e.toString() + " : " + e.calculateChristmasBonus());
        }
    }
}
