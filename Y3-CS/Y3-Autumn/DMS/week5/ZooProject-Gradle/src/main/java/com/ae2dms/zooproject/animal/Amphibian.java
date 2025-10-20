package com.ae2dms.zooproject.animal;

public class Amphibian extends Animal {
    public Amphibian(String name) {
        super(name);
    }

    @Override
    public void eat() {
        System.out.println(this.getClass().getSimpleName() + " eats like an amphibian.");
    }

    @Override
    public void enjoy() {
        System.out.println(this.getClass().getSimpleName() + " enjoys life as amphibian.");
    }

    @Override
    public void maintain() {

    }
}

