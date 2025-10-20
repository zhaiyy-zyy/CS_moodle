package com.ae2dms.zooproject;

import com.ae2dms.zooproject.animal.Amphibian;
import com.ae2dms.zooproject.animal.Animal;
import com.ae2dms.zooproject.animal.Reptile;
import com.ae2dms.zooproject.zoo.Zoo;

import java.util.*;

public class ZooApp {
    public static void main(String[] args) {
        ArrayList<Object> animals = new ArrayList<>();
        Object o = new Reptile("Snake", 4);
        Reptile r = new Reptile("Turtle", 24);

        animals.add(o);
        animals.add(r);
        animals.add(new Amphibian("Frog"));

        while (animals.size() > 0) {
            o = animals.remove(0);
            System.out.println(o.toString());

            ((Animal) o).eat();
            ((Animal) o).enjoy();
            System.out.println();
        }
    }
}
