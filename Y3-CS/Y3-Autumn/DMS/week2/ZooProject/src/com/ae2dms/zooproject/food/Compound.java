package com.ae2dms.zooproject.food;

import com.ae2dms.zooproject.animal.Animal;

import java.util.ArrayList;

/**
 * object of this class represent zoo compounds
 *
 * @author Bryan Lee
 * @version 2.3
 * @since 1.0
 */
public class Compound {
    private final ArrayList<Animal> animals;

    public Compound() {
        animals = new ArrayList<>();
    }

//    public void addAnimal(Animal animal) {
//        animals.add(new Animal());
//    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void printInfo() {
        System.out.println("The compound has " + animals.size() + " animals.");
    }
}
