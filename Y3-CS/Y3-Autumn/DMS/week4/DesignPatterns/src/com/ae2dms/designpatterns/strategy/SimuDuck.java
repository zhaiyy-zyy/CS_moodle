package com.ae2dms.designpatterns.strategy;

public class SimuDuck {
    public static void main(String args[]){
        Duck mallard = new MallardDuck();
        mallard.performFly();

        Duck rubber = new RubberDuck();
        rubber.performFly();

        rubber.setFlyBehavior(new FlyFast());
        rubber.performFly();
    }
}
