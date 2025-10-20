package com.ae2dms.zooproject.animal;

import com.ae2dms.zooproject.misc.Maintainable;

/**
 *
 */
public abstract class Animal implements Maintainable {
    private String name;
    private Feather feather;

    /**
     * This is a constructor for Animal object that
     * take in one parameter.
     *
     * @author Bryan
     * @version 1.1
     * @param name Indicate the name of an animal
     */
    public Animal(String name) {
        this.setName(name);
    }

    public abstract void eat();

    /**
     *
     * @return the name of the animal
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void enjoy() {
        System.out.println(this.getClass().getSimpleName() + " enjoys life as animal.");
    }
}
