package com.ae2dms.zooproject.animal;

import com.ae2dms.zooproject.misc.Maintainable;

public abstract class Animal implements Maintainable {
    private String name;

    public Animal(String name) {
        this.setName(name);
    }

    public abstract void eat();

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


