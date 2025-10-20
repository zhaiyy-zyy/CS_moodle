package com.ae2dms.designpatterns.strategy;

public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("Duck me don't know how to fly!");
    }
}
