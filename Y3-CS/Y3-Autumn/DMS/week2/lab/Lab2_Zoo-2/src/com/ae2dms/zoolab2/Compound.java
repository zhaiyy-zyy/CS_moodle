package com.ae2dms.zoolab2;

import java.util.ArrayList;

public class Compound {
    private ArrayList<Animal> animals;

    public Compound() {
        animals = new ArrayList<Animal>();
    }

    public void addAnimal(Animal animal){
        animals.add(animal);
    }
}
