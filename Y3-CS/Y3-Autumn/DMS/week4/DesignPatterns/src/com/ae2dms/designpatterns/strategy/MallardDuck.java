package com.ae2dms.designpatterns.strategy;

public class MallardDuck extends Duck {
    public MallardDuck() {
        flyBehavior = new FlyFast();
    }

    @Override
    public void display() {
        System.out.print("A MallardDuck flies around in the screen.");
    }
}
