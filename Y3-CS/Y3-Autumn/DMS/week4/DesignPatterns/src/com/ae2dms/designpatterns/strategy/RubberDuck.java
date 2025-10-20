package com.ae2dms.designpatterns.strategy;

public class RubberDuck extends Duck {
    public RubberDuck() {
        flyBehavior = new FlyNoWay();
    }

    @Override
    public void display() {
        System.out.print("A cute rubber duck sits in the screen.");
    }
}
