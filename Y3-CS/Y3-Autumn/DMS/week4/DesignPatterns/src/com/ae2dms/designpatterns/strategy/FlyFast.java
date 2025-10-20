package com.ae2dms.designpatterns.strategy;

public class FlyFast implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("I am a fast-flying duck!");
    }
}
